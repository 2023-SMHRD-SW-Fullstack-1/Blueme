/*
작성자: 신지훈
날짜: 2023-09-25
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 구현, 음악 재생 시간 오류 디버깅, 전체 재생 추가, 모바일화면 텍스트 개행
*/
/*
작성자: 신지훈
날짜: 2023-09-21
설명: 리덕스 musicIds 설정
*/
/*
작성자: 이지희
날짜: 2023-09-25
설명: 리덕스 playingStatus 설정
*/
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import SingleMusic from "../../components/Library/SingleMusic";
import "swiper/swiper-bundle.css";
import axios from "axios";
import { setMusicIds } from "../../store/music/musicActions.js";
import { setCurrentSongId, setPlayingStatus } from "../../store/music/musicActions";

const SavedPlaylistDetail = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const [selectedPlaylistDetails, setSelectedPlaylistDetails] = useState([]);
  const [playlistImage, setPlaylistImage] = useState("");
  const [title, setTitle] = useState("");
  // musicIds 설정
  const [ids, setIds] = useState([]);

  let params = useParams();
  let id = params.id;
  function convertSecondsToMinutes(time) {
    if (isNaN(time)) {
      console.error("Invalid time value:", time);
      return "0:00";
    }

    const minutes = Math.floor(time / 60);
    const seconds = time % 60;

    return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  }
  // 플레이리스트 내 음악리스트 정보
  useEffect(() => {
    const fetchSavedPlaylistDetails = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/savedMusiclist/get/detail/${id}`);
        setSelectedPlaylistDetails(response.data.savedMusiclistDetailsResDto);
        setTitle(response.data.title);
        if (response.data.savedMusiclistDetailsResDto.length > 0) {
          setPlaylistImage(response.data.savedMusiclistDetailsResDto[0].img);
        }
        console.log("response.data", response.data);
        setIds(response.data.savedMusiclistDetailsResDto.map((music) => music.musicId));
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylistDetails();
  }, [id]);

  const handleListClick = () => {
    dispatch(setMusicIds(ids));
  };
  const WholePlaying = () => {
    dispatch(setCurrentSongId(ids[0])); // 첫 번째 음악 재생
    dispatch(setMusicIds(ids));
    dispatch(setPlayingStatus(true));
    navigate(`/MusicPlayer/${ids[0]}`);
  };

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-full text-custom-white p-3 hide-scrollbar overflow-auto mb-[70px]">
      <br />

      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + playlistImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-xl py-5 text-center whitespace-normal">{title}</p>
      </div>
      <div className="flex items-center justify-center">
        <button
          onClick={WholePlaying}
          className="className=bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple  tracking-tight xs:w-[150px] xl:w-[180px] h-10 text-[18px]"
        >
          전체 재생
        </button>
      </div>
      <div className="mt-[20px] xs:mb-14" onClick={handleListClick}>
        {selectedPlaylistDetails.map((music) => (
          <SingleMusic
            key={music.savedMusiclistsDetailId}
            item={{
              musicId: music.musicId,
              img: music.img,
              title: music.musicTitle,
              artist: music.musicArtist,
              genre1: music.musicGenre,
              time: music.time,
            }}
          />
        ))}
      </div>
      <div className="mb-[80px]"></div>
    </div>
  );
};

export default SavedPlaylistDetail;
