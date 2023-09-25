/*
작성자: 이지희
날짜(수정포함): 2023-09-19
설명: 음악 플레이어 수정
*/
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";

// import - 플레이어 아이콘
import { ReactComponent as Prev } from "../assets/img/musicPlayer/backward.svg";
import { ReactComponent as Next } from "../assets/img/musicPlayer/forward.svg";
import { ReactComponent as Play } from "../assets/img/musicPlayer/play.svg";
import { ReactComponent as Pause } from "../assets/img/musicPlayer/pause.svg";

// import - 이미지
import likeEmpty from "../assets/img/likeEmpty.png";
import likeFull from "../assets/img/likeFull.png";
import rotate from "../assets/img/musicPlayer/rotate.png";
import rotating from "../assets/img/musicPlayer/rotating.png";

import ProgressBar from "../components/music/ProgressBar"

// Redux - 음악 관련
import {
  setCurrentSongId,
  setPlayingStatus,
  setCurrentTime,
  setDraggingStatus,
  setRepeatMode,
  setIsMusicPlayer
} from "../store/music/musicActions";

// component

const MusicPlayer = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // 사용자 id
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id

  // 음악 관련 정보
  const [musicInfo, setMusicInfo] = useState({
    album: "",
    title: "",
    artist: "",
    img: ""
  });
  // 좋아요 버튼 관련
  const [isSaved, setIsSaved] = useState(-1); // 초기 좋아요 상태 불러오기
  const [isLiked, setIsLiked] = useState(isSaved > 0 ? true : false);
  // 음악 재생 인덱스 (리덕스 활용)
  const musicIds = useSelector((state) => state.musicReducer.musicIds);
  const currentSongId = useSelector((state) => state.musicReducer.currentSongId);
  const playingStatus = useSelector((state) => state.musicReducer.playingStatus);
  const currentTime = useSelector((state) => state.musicReducer.currentTime);
  const duration = useSelector((state) => state.musicReducer.duration);
  const repeatMode = useSelector((state) => state.musicReducer.repeatMode);


// 뮤직플레이어 접속 여부 확인 (음악 자동재생용)
useEffect(()=>{
  dispatch(setIsMusicPlayer(true));
}, [dispatch]);

  // 서버에서 음악 정보 가져오기
  useEffect(() => {
    const fetchMusicInfo = async () => {
      try {
        const response = await axios.get(`/music/info/${currentSongId}`);
        setMusicInfo({
          album: response.data.album,
          title: response.data.title,
          artist: response.data.artist,
          img: response.data.img
        });
        
      } catch (error) {
        console.error("음악 정보 가져오기 실패", error);
      }
    };
    fetchMusicInfo();
  }, [currentSongId]);

  // 이전곡
  const prevTrack = () => {
    const index = musicIds.indexOf(Number(currentSongId));
    let prevIndex = index - 1;

    if (prevIndex < 0) {
      prevIndex = musicIds.length - 1;
    }

    const prevSongId = musicIds[prevIndex];
    dispatch(setCurrentSongId(prevSongId));
    navigate(`/MusicPlayer/${prevSongId}`);
  };

  // 다음 곡
  const nextTrack = () => {
    const index = musicIds.indexOf(Number(currentSongId));
    let nextIndex = index + 1;

    if (nextIndex >= musicIds.length) {
      nextIndex = 0;
    }

    const nextSongId = musicIds[nextIndex];
    dispatch(setCurrentSongId(nextSongId));
    navigate(`/MusicPlayer/${nextSongId}`);
  };

  useEffect(() => {
    const fetchLikeStatusAndRecent = async () => {
      try {
        // 좋아요 상태 확인
        const likeRequest = axios.post("/likemusics/issave", {
          userId: userId.toString(),
          musicId: currentSongId.toString(),
        });

        // 최근 재생목록 추가
        const recentRequest = axios.post("/playedmusic/add", {
          userId: userId.toString(),
          musicId: currentSongId.toString(),
        });

        const [likeResponse] = await Promise.all([likeRequest, recentRequest]);

        setIsSaved(parseInt(likeResponse.data));
        setIsLiked(parseInt(likeResponse.data) > 0);
      } catch (error) {
        console.error("좋아요 상태 가져오기 실패", error);
      }
    };

    fetchLikeStatusAndRecent();
  }, [userId, currentSongId]);

  // 좋아요버튼 누르기
  const toggleLike = async () => {
    try {
      await axios.put("/likemusics/toggleLike", {
        userId: userId.toString(),
        musicId: currentSongId,
      });
      setIsLiked(!isLiked);
    } catch (error) {
      console.error("좋아요 등록 실패", error);
    }
  };

   // 재생시간 표시
   const formatTime = (timeInSeconds) => {

    timeInSeconds = Number(timeInSeconds);

    const minutes = Math.floor(timeInSeconds / 60);
    const seconds = Math.floor(timeInSeconds % 60);
  
    return `${minutes < 10 ? "0" : ""}${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  };

return (
    <div className="full-screen-player">
      <p className="py-[10px]">{musicInfo.album}</p>
      <div className=" w-[300px]">
        <img
          src={"data:image/;base64," + musicInfo.img}
          alt=""
          className="h-auto rounded-lg"
        />
        <p className="text-xl pt-[10px] ">{musicInfo.title}</p>
        <p>{musicInfo.artist}</p>
      </div>
      {/* 재생바 */}
      <ProgressBar currentTime={currentTime} duration={duration} playingStatus={playingStatus} />
        <div className="flex justify-between text-custom-gray mt-4" style={{width: '84%'}}> 
              <span>{formatTime(currentTime)}</span>
              <span>{formatTime(duration)}</span>
        </div>
      <div
        className="flex flex-row justify-center items-center gap-10 mt-5">
        <img
          className= "w-[25px] h-auto"
          alt=""
          src={repeatMode ? rotating : rotate}
          onClick={() => dispatch(setRepeatMode(!repeatMode))}
        />
        <Prev
          className= "w-[50px] h-auto"
          onClick={prevTrack}
        />
        {playingStatus ? (
          <Pause
            className="w-[50px] h-auto"
            onClick={() => {
              dispatch(setPlayingStatus(false));
            }}
          />
        ) : (
          <Play
            className="w-[50px] h-auto"
            onClick={() => {
              dispatch(setPlayingStatus(true));
            }}
          />
        )}

        <Next
          className="w-[50px] h-auto"
          onClick={nextTrack}
        />
        <div className="ml-auto">
          <button onClick={toggleLike}>
            <img
              className= "w-[30px] h-auto"
              src={isLiked ? likeFull : likeEmpty}
              alt="like-button"
            />
          </button>
        </div>
      </div>
      {/* <img
        src={scroll}
        onClick={() => navigate("/library")}
        className= "w-[40px] h-auto fixed bottom-[10%]"
      /> */}
    </div>
  );
};

export default MusicPlayer;
