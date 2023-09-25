/*
작성자: 신지훈
날짜: 2023-09-25
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 , 모달창 구현 및 디자인 수정, musicIds 설정, 전체 재생 구현, 모바일화면 텍스트 개행
*/
/*
작성자: 이지희
날짜: 2023-09-18
설명: MusicIds 리덕스 상태관리 추가
*/

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import SingleMusic from "../../components/Library/SingleMusic";

import "swiper/swiper-bundle.css";
import axios from "axios";
import check from "../../assets/img/check.json";
import Lottie from "lottie-react";
import { useSelector, useDispatch } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";

import { setCurrentSongId } from "../../store/music/musicActions";

const ThemePlaylist = () => {
  // 상태 변수 초기화
  const [themeImage, setThemeImage] = useState("");
  const [themeName, setThemeName] = useState("");
  const [musicList, setMusicList] = useState([]);
  const [showThemePlaylistModal, setShowThemePlaylistModal] = useState(false);

  const navigate = useNavigate();

  // Redux에서 사용자 정보와 음악 ID 가져오기
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  const dispatch = useDispatch();

  const [ids, setIds] = useState([]);

  // 플레이리스트 상세 정보 가져오기
  useEffect(() => {
    const getPlaylistDetails = async () => {
      try {
        // 로컬 스토리지에서 이미지 및 테마 이름 가져오기
        const imageFromStorage = localStorage.getItem("themeImage");
        if (imageFromStorage) {
          setThemeImage(imageFromStorage);
        }

        const nameFromStorage = localStorage.getItem("themeName");
        if (nameFromStorage) {
          setThemeName(nameFromStorage);
        }

        const themeIdFromStorage = localStorage.getItem("themeId");

        if (themeIdFromStorage) {
          // API를 통해 음악 리스트 가져오기
          const responseMusicList = await axios.get(`/theme/themelists/${themeIdFromStorage}`);

          if (responseMusicList.data) {
            setMusicList(responseMusicList.data);
            setIds(responseMusicList.data.map((music) => music.musicId));
          }
        }
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    getPlaylistDetails();
  }, []);

  // 음악 리스트 전체 저장
  const saveMusicList = async () => {
    try {
      const dataToSend = {
        userId: userId.toString(),
        title: themeName,
        musicIds: musicList.map((item) => item.musicId),
        image: themeImage,
      };

      // 서버에 데이터 저장 요청
      await axios.post("http://172.30.1.27:8104/savedMusiclist/add", dataToSend);
      // console.log("Saved music list");
      setShowThemePlaylistModal(true);
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  // Lottie 애니메이션 스타일 설정
  const Lottiestyle = {
    weight: 70,
    height: 70,
  };

  // 모달 닫기 함수
  const closeModal = () => setShowThemePlaylistModal(false);

  // MusicIds 설정
  const handleListClick = () => {
    dispatch(setMusicIds(ids));
  };

  const WholePlaying = () => {
    dispatch(setCurrentSongId(ids[0])); // 첫 번째 음악을 재생
    dispatch(setMusicIds(ids));
    navigate(`/MusicPlayer/${ids[0]}`);
  };

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-full text-custom-white p-3 hide-scrollbar overflow-auto mb-[70px]">
      <br />

      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + themeImage} className="w-[160px] h-[160px] rounded-xl" alt="Theme" />
        <p className="text-xl py-5 text-center whitespace-normal">{themeName}</p>
      </div>

      <div className="flex justify-between mb-5 mt-2">
        <button
          onClick={WholePlaying}
          className="bg-gradient-to-t  from-gray-800 border ml-2 xs:w-[150px] xl:w-[180px] rounded-lg text-custom-lightpurple font-semibold tracking-tighter  h-10 text-xl"
        >
          전체 재생
        </button>
        <button
          onClick={saveMusicList}
          className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter xs:w-[150px] xl:w-[180px]  h-10 text-xl"
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
        <div className=" bg-gray-900 w-[300px] mx-auto rounded-lg shadow-lg z-50 overflow-y-auto border-2 border-white">
          <div className=" py-[20px] text-left  text-custom-white px-[30px]  bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800 ">
            <Lottie
              animationData={check}
              style={Lottiestyle}
              loop={false}
              autoplay={true}
              key={Date.now()}
              className="flex justify-center items-center mb-3"
            />

            <p className="text-center">저장이 완료되었습니다!</p>

            <div className="flex justify-end pt-2">
              <button
                onClick={closeModal}
                className="w-auto bg-transparent hover:bg-blue text-blue-dark font-semibold hover:text-white border-0 px-[10px] border-blue hover:border-transparent rounded"
              >
                확인
              </button>
            </div>
          </div>
        </div>
      </div>

      <div className="h-[70%] ml-3 mr-3 overflow-y-scroll hide-scrollbar" onClick={handleListClick}>
        {musicList.map((item) => (
          <SingleMusic key={item.id} item={item} className="mb-20" />
        ))}
      </div>
    </div>
  );
};

export default ThemePlaylist;
