/*
작성자: 이유영
날짜: 2023-09-16
설명: 로그아웃 및 사용자 정보 가져오기
*/
/*
작성자: 이지희
날짜: 2023-09-27
설명: 로그아웃 시 음악 정보 삭제
*/
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import user from "../../assets/img/defalut.png";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import { useLocation } from "react-router-dom";
import Login from "../Login";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { userUpdate, logoutSuccess } from "../../store/member/memberAction";
import { clearPlaying } from "../../store/music/musicActions";
import basicProfile from '../../assets/img/basicProfile.jpg'

axios.defaults.baseURL = "http://localhost:3000/";
axios.defaults.withCredentials = true;
const MyPage = () => {
  // const [singers, setSingers] = useState([]);
  const [genres, setGenres] = useState([]);
  const [artists, setArtists] = useState([]);
  const location = useLocation();
  const navigate = useNavigate();
  const [img, setImg] = useState("");
  const [selectedGenres, setSelectedGenres] = useState([]); // 추가: 선택한 장르를 저장할 상태
  const selectedArtistsFromRecommendation = location.state?.selectedArtists || [];
  const user = useSelector((state) => state.memberReducer.user);
  const id = user.id;
  const platFormType = user.platFormType;
  const dispatch = useDispatch();

  //마이페이지 들어가면 장르 , 아티스트 , 사용자 정보 받아서 화면 렌더링
  useEffect(() => {
      axios
      .get(`${process.env.REACT_APP_API_BASE_URL}/user/Mypage`,{
        headers : {Authorization : localStorage.getItem('accessToken')}
      })
      .then((res) => {
        // console.log('전체 res',res);
        setGenres(res.data[0].genres)//장르
        setArtists(res.data[0].artists)//아티스트
        setImg(res.data[0].imgUrl)//프로필
      })
      .catch((err) => console.log(err));
  }, []);

  //로그아웃 => 토큰 전체 삭제
  const handleLogout = (accessToken) => {
    localStorage.clear();
    dispatch(logoutSuccess());
    dispatch(clearPlaying())
    navigate('/')
  }

  return (
    <div className="font-semibold overflow-scroll flex justify-center items-center h-screen bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 ">
    
    <div className="xs:mt-[250px] mt-[100px] xs:w-full xl:w-[1250px]">
      <br /><br /><br /><br />
      {/* 사용자 프로필 */}
    <div className="flex justify-between items-center ">
      <div className="flex items-center boar ">
        {user.img_url === null? 
        <img 
        src={basicProfile}
        alt="profile"
        className="w-[70px] h-[70px] mb-3 mt-2 rounded-sm "
        /> : (user.platFormType === "blueme" ? <img
        src={"data:image/;base64," +user.img_url}
        alt="userImg"
        className="w-[65px] h-[65px] mb-3 mt-2 rounded-sm "
      /> : <img
      src={user.img_url}
      alt="userImg"
      className="w-[65px] h-[65px] mb-3 mt-2 rounded-sm "
    />)
        }
        <span className="pt-1 ml-3 text-[17px] font-normal overflow-hidden h-[58px] mr-10">{user.nickname}</span>
      </div>
      <div className="mt-3">
        {platFormType === "blueme" && 
          <Link to="/MemberInfoChange">
            <button className=" bg-gradient-to-t from-gray-600 rounded-2xl text-sm h-8 w-20 p-1">
              내 프로필
            </button>
          </Link>
         } 
      </div>
    </div>

    {/* 사용자 정보(email, nickname) */}
    <div className="text-xl mt-4">내 정보
    <div className="mt-2">
      <p className="bg-gradient-to-t from-gray-900 overflow-hidden h-[42px] font-normal text-base tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2  mt-3 rounded-lg text-custom-white peer  bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
      {user.email}
      </p>
    </div>
    <div className="mt-2 sm:mt-2 md:mt-2">
    <p className="bg-gradient-to-t from-gray-900 overflow-hidden text-base h-[42px] font-normal tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2  mt-3 rounded-lg text-custom-white peer  bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
      {user.nickname}</p>

      {/* 선호하는 음악 장르  */}
      <div className="mt-10">
        <div className="text-xl">당신이 좋아하는 음악 장르는?</div>
        <div className="flex w-full mt-5 text-base justify-start">
          <Swiper
            spaceBetween={20}
            slidesPerView={2}
            onSlideChange={() => {}}
            onSwiper={()=> {}}
            className="w-[280px] flex"
          >           
            {genres && genres.map((genre) => (
              <SwiperSlide key={genre.genreId}>
                <button className="flex flex-col items-center text-center space-y-4 mt-2 justify-center">
                  <img
                    src={"data:image/;base64,"+ genre.img}
                    alt="genre image"
                    className="rounded-lg border object-cover blur-[1.5px] w-[130px] h-[130px]"
                  />
                  <h5 className="font-normal absolute text-2xl">{genre.name}</h5>
                </button>
              </SwiperSlide>
            ))}
          </Swiper>
          <Link
            to="/SelectGenre"
            className="text-custom-lightgray w-30 ml-5 self-center text-sm w-[50px]"
          >더보기</Link>
        </div>
      </div>
      </div>
      {/* 선호하는 아티스트 */}
      <div className="text-xl mt-8">당신이 좋아하는 아티스트는?</div>
      <div className="flex items-start w-full mt-2 justify-start">
        <Swiper
          spaceBetween={20}
          slidesPerView={2} 
          onSlideChange={() => {}}
          onSwiper={() => {}}
          className="w-[280px] flex"
        >
          {artists && artists.map((artist) => (
            <SwiperSlide key={artist.artistFilePath}>
              <button className="flex flex-col items-center text-center space-y-4 mt-2 justify-center">
                <img
                  src={"data:image/;base64," + artist.img}
                  alt="artist image"
                  className="rounded-lg img-fixed border object-cover blur-[1.5px]  w-[130px] h-[130px]"
                />
                <h5 className="font-normal absolute text-2xl
                transform -translate-x-half -translate-y-half 
                left-half top-half 
                -translate-x[-50%] -translate-y[-50%]">{artist.artistName}</h5>
              </button>
            </SwiperSlide>
          ))}
        </Swiper>
        <Link
          to="/Artistrecommend"
          className="text-custom-lightgray w-30 ml-5 self-center text-sm w-[50px]"
        >더보기</Link>
      </div>
      
      {/* 로그아웃 */}
      <div className=" 
             flex
             item-center
             justify-center
             mt-10
             mb-[80px]
             h-11
             px-3 relative 
             bg-gradient-to-t from-gray-800
             rounded-lg border
             text-custom-lightgray
             tracking-tight
            w-full
            "
          >
            <button onClick={handleLogout} className="text-center">
              로그아웃
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
