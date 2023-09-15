/*
작성자: 이유영
날짜: 2023-09-12
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


const MyPage = () => {
  const [singers, setSingers] = useState([]);
  const [genre, setGenre] = useState([])
  const [selectGenre1, setSelectGenre1] = useState ([]);
  const [selectGenre2, setSelectGenre2] = useState ([]);
  const location = useLocation();
  const navigate = useNavigate();
  const [selectedGenres, setSelectedGenres] = useState ([]); // 추가: 선택한 장르를 저장할 상태
  const selectedArtistsFromRecommendation =location.state?.selectedArtists || [];
  const genre1 = localStorage.getItem('selectGenre1')
  const genre2 = localStorage.getItem('selectGenre2')
  const id = localStorage.getItem('id')


  // useEffect(() => {
  //     axios.get("http://172.30.1.45:8104/Mypage")
  //     .then((res) => {
  //       console.log(res);
  //       setGenre(res.data)
  //       // 로컬 스토리지에서 선택한 장르 불러오기
  //       for (let i = 0; i< genre.length; i++) {
  //           if(genre1 == i) {
  //             console.log(i);
  //             setSelectGenre1(genre[i]); 
  //           }else if(genre2 == i) {
  //             setSelectGenre2(genre[i])
  //           }
  //       }

          
  //     })
  //     .catch((err) => console.log(err))
  // }, [])
  // console.log("11",selectGenre1, selectGenre2);
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
    navigate('/')
  }

  return (
    <div>
    {localStorage.getItem('accessToken') !== null ? 
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 flex flex-col min-h-screen text-2xl font-semibold tracking-tighter">
      {/* 사용자 프로필 */}
    <div className="flex justify-between items-center mt-20">
      <div className="flex items-center boar">
        <img
          src={user}
          alt=""
          className="w-[70px] h-[70px] mb-3 mt-2 rounded-lg"
        />
        <span className="pt-1 ml-3 text-[20px] ">{localStorage.getItem('email')}</span>
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
    <div className="text-2xl mt-6">내 정보</div>
    <div className="mt-2 sm:mt-2 md:mt-2">
      <p className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-[386px] mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
      {localStorage.getItem('email')}
      </p>
    </div>
    <div className="mt-2 sm:mt-2 md:mt-2 ">
    <p className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-[386px] mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
      {localStorage.getItem('nickname')}</p>

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
            {singers.map((singer) => (
              <SwiperSlide key={singer.id}>
                <button class="flex flex-col items-center text-center space-y-4 mt-2 justify-center">
                  <img
                    src={singer.image}
                    alt="singer image"
                    class="rounded-lg border object-cover blur-sm"
                  />
                  <h5 className="font-semibold absolute">{singer.name}</h5>
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
          slidesPerView={2} // Reduce the number of slides per view
          onSlideChange={() => {}}
          onSwiper={(swiper) => {}}
          className="w-[280px] flex"
        >
          {singers.map((singer) => (
            <SwiperSlide key={singer.id}>
              <button class="flex flex-col items-center text-center space-y-4 mt-2 justify-center">
                <img
                  src={singer.image}
                  alt=""
                  class="rounded-lg img-fixed border object-cover blur-sm"
                />
                <h5 class="font-semibold absolute">{singer.name}</h5>
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
      <div className="text-custom-white mt-6 text-[15px] text-center">
        <button onClick={handleLogout}>로그아웃</button>
      </div>
    </div>
  </div> : <Login/ >}
  
  </div>
  );
};

export default MyPage;
