/*
작성자: 이지희
날짜(수정포함): 2023-09-16
설명: 사용자 라이브러리
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-25
설명: 사용자 라이브러리 웹 디자인 및 반응형 완료 , 디버깅 완료
*/

import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import LikedList from "../../components/Library/LikedList";
import SavedPlaylist from "../../components/Library/SavedPlaylist";
import { Swiper, SwiperSlide } from "swiper/react";
import BeforeRegistration from "../../components/Main/BeforeRegistration";

const Library = () => {
  // 사용자의 추천 음악 목록 상태 변수
  const [myRecMusicList, setMyRecMusicList] = useState([]);
  // Redux를 통해 사용자 정보 및 로그인 상태 가져오기
  const user = useSelector((state) => state.memberReducer.user);
  const id = user.id;
  const isLoggendIn = useSelector((state) => state.memberReducer.isLogin);
  const navigate = useNavigate();
  const [myMusicIds, setMyMusicIds] = useState([]); // 나의 플리 musicId

  useEffect(() => {
    if (isLoggendIn) {
      // 로그인한 경우 사용자의 추천 플레이리스트 불러오기
      axios
        .get(`http://172.30.1.27:8104/recMusiclist/${id}`)
        .then((res) => {
          setMyRecMusicList(res.data); // 추천 플레이리스트 저장
          setMyMusicIds(res.data.map((myRecMusicList) => myRecMusicList.recMusiclistId));
          console.log("my", res);
        })
        .catch((err) => console.log(err));
    }
  }, []);

  return (
    <div className="overflow-auto hide-scrollbar bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-2 h-full font-semibold tracking-tighter">
      <br />
      <div className="flex flex-col md:flex-row">
        <div className="w-full md:w-1/2 pr-0 md:pr-2 order-last md:order-first lg:pr-10 md:pt-10">
          <p className="text-left text-xl ml-[5px] pt-[20px] lg:pt-[30px] lg:text-3xl lg:ml-8 ">저장한 플레이리스트</p>
          <SavedPlaylist />

          <h1 className="text-left indent-1 text-xl font-semibold tracking-tighter mt-1 hidden md:block lg:text-3xl  lg:ml-8">
            Chat GPT가 추천해준 나의 플레이리스트
          </h1>

          {isLoggendIn && myRecMusicList.length > 0 && (
            <Swiper
              className=" mt-5 lg:mt-10 hidden md:block overflow-hidden xs:mb-20"
              spaceBetween={10}
              slidesPerView="0"
              breakpoints={{
                320: {
                  slidesPerView: 2,
                  spaceBetween: 10,
                },

                480: {
                  slidesPerView: 2,
                  spaceBetween: 10,
                },

                640: {
                  slidesPerView: 2,
                  spaceBetween: 10,
                },

                768: {
                  slidesPerView: 3,
                  spaceBetween: 10,
                },
                1024: {
                  slidesPerView: 3,
                  spaceBetween: 10,
                },
              }}
            >
              {myRecMusicList &&
                myRecMusicList.map((item, i) => (
                  <SwiperSlide key={item.recMusiclistId} className="">
                    <div>
                      <img
                        onClick={() => {
                          navigate(`/RecPlayListDetail/${myMusicIds[i]}`);
                        }}
                        src={"data:image/;base64," + item.recMusiclistDetails[0].img}
                        alt="album cover"
                        className="w-[200px] h-auto rounded-lg sm:h-auto mr-20"
                      />
                      <p className="tracking-tighter text-sm text-center mt-2 lg:text-base lg:mr-20 ">{item.title}</p>
                    </div>
                  </SwiperSlide>
                ))}
            </Swiper>
          )}
        </div>

        <div className="w-full md:w-1/2 pl-0 md:pl-2 mt-[30px] md:mt-[10px] order-first md:order-last md:pt-10  lg:pr-10">
          <div className="flex mt-14 xs:mt-5 mb-3">
            <span className="text-left text-xl ml-[5px]  lg:pt-[0px] lg:text-3xl lg:mb-5 ">좋아요 누른 음악 목록</span>
            <button className="ml-auto text-custom-gray mr-2 text-sm">
              <Link to="/LikedPlaylist">더보기</Link>
            </button>
          </div>
          <LikedList />{" "}
        </div>
      </div>
    </div>
  );
};

export default Library;
