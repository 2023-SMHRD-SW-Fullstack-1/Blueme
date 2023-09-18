/*
작성자: 신지훈
날짜: 2023-09-16
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 , 모달창 구현
*/
/*
작성자: 이지희
날짜: 2023-09-18
설명: MusicIds 리덕스 상태관리 추가
*/

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import SingleMusic from "../../components/Library/SingleMusic";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";
import axios from "axios";
import check from "../../assets/img/check.json";
import Lottie from "lottie-react";
// 지희시작
import { useSelector, useDispatch } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";
// 지희 끝

const ThemePlaylist = () => {
  const [themeImage, setThemeImage] = useState("");
  const [themeName, setThemeName] = useState("");
  const [musicList, setMusicList] = useState([]);
  const [showThemePlaylistModal, setshowThemePlaylistModal] = useState(false);

// 지희 시작(0918)
const dispatch = useDispatch();

const musicIds = useSelector(state => state.musicReducer.musicIds);
const [ids, setIds] = useState([]);
// 지희 끝

  useEffect(() => {
    const getPlaylistDetails = async () => {
      try {
        const imageFromStorage = localStorage.getItem("themeImage");
        if (imageFromStorage) {
          setThemeImage(imageFromStorage);
          // console.log(imageFromStorage);
        }

        const nameFromStorage = localStorage.getItem("themeName");
        if (nameFromStorage) {
          setThemeName(nameFromStorage);
        }

        const themeIdFromStorage = localStorage.getItem("themeId");

        if (themeIdFromStorage) {
          const responseMusicList = await axios.get(`/theme/themelists/${themeIdFromStorage}`);
          // .then((res) => console.log(res.data));

          if (responseMusicList.data) {
            setMusicList(responseMusicList.data);
          }

        }
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    getPlaylistDetails();
  }, []);

  const saveMusicList = async () => {
    try {
      // if (!themeName) {
      //   console.error("No theme selected");
      //   return;
      // }

      const dataToSend = {
        userId: "1",
        title: themeName,
        musicIds: musicList.map((item) => item.musicId), //여기 다시 하기 musicId
        // image: themeImage,
      };
      await axios.post("http://172.30.1.27:8104/savedMusiclist/add", dataToSend);
      console.log("Saved music list");
      setshowThemePlaylistModal(true); // Add this line
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  const Lottiestyle = {
    weight: 70,
    height: 70,
  };

  const closeModal = () => setshowThemePlaylistModal(false);

 // 지희(0918) - MusicIds 설정
 useEffect(() => {
  const newIds = musicList.map(item => item.musicId);
  setIds(newIds);
}, [musicList]);

 const handleListClick = () => {
  dispatch(setMusicIds(ids));
};
// 지희 끝

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <br />

      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + themeImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{themeName}</p>
      </div>

      <div className="flex justify-between mb-5 mt-2">
        <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
          전체 재생
        </button>
        <button
          onClick={saveMusicList}
          className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl"
        >
          전체 저장
        </button>
      </div>

      <div
        id="popup-modal"
        tabIndex="-1"
        className={`fixed top-0 left-0 right-0 bottom-0 z-50 p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] text-custom-black max-h-full ${
          showThemePlaylistModal ? "" : "hidden"
        } flex items-center justify-center`}
      >
        <div className=" absolute w-full h-full bg-gray-900 opacity-50"></div>
        <div className=" bg-white w-[300px] mx-auto rounded shadow-lg z-50 overflow-y-auto">
          {/* 모달 컨텐츠 */}
          <div className=" py-[20px] text-left px-[30px]">
            {/* Modal Header */}
            <Lottie
              animationData={check}
              style={Lottiestyle}
              loop={false}
              autoplay={true}
              key={Date.now()}
              className="flex justify-center items-center mb-3"
            />

            {/* Modal Body */}
            <p className="text-center">저장이 완료되었습니다!</p>

            {/* Modal Footer */}
            <div className="flex justify-end pt-2">
              <button
                onClick={closeModal}
                className="w-auto bg-transparent hover:bg-blue text-blue-dark font-semibold hover:text-white border-0 px-[10px] border border-blue hover:border-transparent rounded"
              >
                확인
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Render music list */}
      {/* 지희 시작(0918) - div추가 및 onClick함수 세팅 */}
     
      {musicList.length > 0 ? (
        <Swiper direction={"vertical"} slidesPerView={6.2} spaceBetween={1} className="h-[49%] ml-3 mr-3" onClick={handleListClick}>
          {musicList.map((item) => (
            <SwiperSlide key={item.id}>
              <SingleMusic item={item} />
            </SwiperSlide>
          ))}
        </Swiper>
      ) : (
        <p>No music available</p>
      )}
      
    </div>
  );
};

export default ThemePlaylist;
