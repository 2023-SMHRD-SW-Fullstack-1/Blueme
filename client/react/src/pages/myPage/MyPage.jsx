/*
작성자: 이유영
날짜: 2023-09-16
설명: 로그아웃 및 사용자 정보 가져오기
*/
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import user from "../../assets/img/defalut.png";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import { useLocation } from "react-router-dom";
import Login from "../Login";
import axios from 'axios';
import { useDispatch, useSelector } from "react-redux";
import { userUpdate, logoutSuccess } from "../../store/member/memberAction";
import profile from '../../assets/img/basicProfile.jpg'


const MyPage = () => {
  // const [singers, setSingers] = useState([]);
  const [genres, setGenres] = useState([])
  const [artists, setArtists] = useState([])
  const location = useLocation();
  const navigate = useNavigate();
  const [img, setImg] = useState("")
  const [selectedGenres, setSelectedGenres] = useState ([]); // 추가: 선택한 장르를 저장할 상태
  const selectedArtistsFromRecommendation =location.state?.selectedArtists || [];
  const user = useSelector(state => state.memberReducer.user)
  const id = user.id
  // console.log('header',user.img_url);
  const dispatch = useDispatch()



  useEffect(() => {
      axios
      .get(`http://172.30.1.45:8104/user/Mypage/${id}`)
      .then((res) => {
        console.log('전체 res',res);
        setGenres(res.data[0].genres)
        setArtists(res.data[0].artists)
        setImg(res.data[0].imgUrl)
        console.log('img',img);
        // console.log(res.data[0].artists);
        // const genreIds = genres.map(genre => genre.genreId);
        // const artistIds = genres.map(artist => artist.artistFilePath);
        // console.log('장르',genreIds); // genreId들의 배열 출력


          // for (let i = 0; i< genres.length; i++) {
          //     if(genres[i] == 0) {
          //       console.log(genres);
          //       // setSelectGenre1(genre[i]); 
          //     }else if(genres[i] == 1) {
          //       console.log(genres);
          //       // setSelectGenre2(genre[i])
          //     }
          // }      
      })
      .catch((err) => console.log(err))
  }, [])

  // useEffect(()=> {
  //   document.getElementById('toast-warning').classList.add("reveal")
  // }, [user.nickname])
  // console.log("11",genres[0], artists[0]);
  // console.log("22",selectedGenres);


   // 선택한 장르 이름을 찾는 함수
  //  const findGenreNameById = (id) => {
    
  //   return genreItem ? genreItem.name : '';
  //   console.log('이름',genreItem);
  // }
  // findGenreNameById()
  
  //로그아웃 => 토큰 전체 삭제
  const handleLogout = (accessToken) => {
    localStorage.clear()
    dispatch(logoutSuccess());
    navigate('/')
  }
console.log(typeof(user.img_url));
  return (
    <div>
    {localStorage.getItem('accessToken') !== null ? 
    <div className="overflow-auto mb-16 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full pb-20 hide-scrollbar">
      {/* 사용자 프로필 */}
    <div className="flex justify-between items-center mt-20">
      <div className="flex items-center boar">
        {user.img_url == null || typeof(user.img_url) == String ? 
        <img 
          src={profile}
          alt="userImg"
          className="w-[70px] h-[70px] mb-3 mt-2 rounded-lg"
          /> : 
        <img
          src={user.img_url}
          alt="userImg"
          className="w-[70px] h-[70px] mb-3 mt-2 rounded-lg"
        />}
        <span className="pt-1 ml-3 text-[20px] ">{user.nickname}</span>
      </div>
      <div className="mt-3 ">
        <Link to="/MemberInfoChange">
          <button className=" bg-gradient-to-t from-gray-600 rounded-2xl text-sm h-8 w-20 p-1">
            내 프로필
          </button>
        </Link>
      </div>
    </div>

    {/* 사용자 정보(email, nickname) */}
    <div className="text-xl mt-4">내 정보</div>
    <div className="mt-2 sm:mt-2 md:mt-2">
      <p className="bg-gradient-to-t from-gray-900 h-[45px] font-normal text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-[386px] mt-3 rounded-lg text-custom-white peer  bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
      {user.email}
      </p>
    </div>
    <div className="mt-2 sm:mt-2 md:mt-2">
    <p className="bg-gradient-to-t from-gray-900 h-[45px] font-normal text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-[386px] mt-3 rounded-lg text-custom-white peer  bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
      {user.nickname}</p>

      {/* 선호하는 음악 장르  */}
      <div className="mt-10 ">
        <div className="text-2xl">당신이 좋아하는 음악 장르는?</div>
        <div className="flex w-full mt-5 text-base">
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
                  <h5 className="font-semibold absolute text-3xl">{genre.name}</h5>
                </button>
              </SwiperSlide>
            ))}
          </Swiper>
          <Link
            to="/SelectGenre"
            className="text-custom-lightgray w-30 ml-5 mr-8 self-center text-byte"
          >더보기</Link>
        </div>
      </div>
      {/* 선호하는 아티스트 */}
      <div className="text-2xl mt-5">당신이 좋아하는 아티스트는?</div>
      <div className="flex items-start w-full mt-5 text-base">
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
                <h5 className="font-semibold absolute text-2xl">{artist.artistName}</h5>
              </button>
            </SwiperSlide>
          ))}
        </Swiper>
        <Link
          to="/Artistrecommend"
          className="text-custom-lightgray w-30 ml-5 mr-8 self-center text-byte"
        >더보기</Link>
      </div>
      
      {/* 로그아웃 */}
      <div className="flex items-center justify-center pl-2 w-full mt-10 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
        <button onClick={handleLogout}>로그아웃</button>
      </div>
    </div>
  </div> : <Login/ >}
  
  </div>
  );
};

export default MyPage;
