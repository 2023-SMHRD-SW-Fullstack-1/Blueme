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
import scroll from "../assets/img/musicPlayer/scrollDown.png";
import rotate from "../assets/img/musicPlayer/rotate.png";
import rotating from "../assets/img/musicPlayer/rotating.png";

// Redux - 음악 관련
import {
  setCurrentSongId,
  setPlayingStatus,
  setCurrentTime,
  setDraggingStatus,
  setRepeatMode
} from "../store/music/musicActions";

const MusicPlayer = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // 사용자 id
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id

  // useState
  const [duration, setDuration] = useState(0);
  // 재생바 이동 관련 useState
  // 한곡반복
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
  const [currentSongIndex, setCurrentSongIndex] = useState(-1);
  const currentSongId = useSelector((state) => state.musicReducer.currentSongId);
  const playingStatus = useSelector((state) => state.musicReducer.playingStatus);
  const currentTime = useSelector((state) => state.musicReducer.currentTime);
  const draggingStatus = useSelector((state) => state.musicReducer.draggingStatus);
  const repeatMode = useSelector((state) => state.musicReducer.repeatMode);

  // 서버에서 음악 정보 가져오기
  useEffect(() => {
    const fetchMusicInfo = async () => {
      try {
        const response = await axios.get(`/music/info/${currentSongId}`);
        // console.log(response.data.time);
        setMusicInfo({
          album: response.data.album,
          title: response.data.title,
          artist: response.data.artist,
          img: response.data.img
        });
        setDuration(parseInt(response.data.time));
      } catch (error) {
        console.error("음악 정보 가져오기 실패", error);
      }
    };
    fetchMusicInfo();
  }, [currentSongId]); // songId 변경 시마다 재실행

  // 이전곡&다음곡
  useEffect(() => {
    const index = musicIds.indexOf(Number(currentSongId));
   setCurrentSongIndex(index);
    setCurrentSongId(Number(currentSongId));
  }, [musicIds]);

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
  }, [currentSongId]);

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

  // 사용자 재생바 조작
  const changeCurrentTime = (e) => {
    let newCurrentTime = e.target.value;
    dispatch(setCurrentTime(newCurrentTime));
  };
  
  // 드래그 시작 - 음악 정지
  const handleDragStart = () => {
    dispatch(setDraggingStatus(true));
    if(playingStatus){
      dispatch(setPlayingStatus(false));
    }
  };

  // 드래그 시작 - 음악 재시작
  const handleDragEnd = () => {
    dispatch(setDraggingStatus(false));
    if(!playingStatus){
      dispatch(setPlayingStatus(true));
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
      <div className="w-[85%] h-2.5 bg-black rounded-full mt-10 relative">
        <div
          style={{ width: `${(currentTime / duration) * 100}%` }}
          className="h-2 bg-white rounded-full absolute"
        />
        <input
          type="range"
          min={0}
          max={duration || 1}
          value={currentTime}
          onChange={changeCurrentTime}
          onMouseDown={handleDragStart}
          onMouseUp={handleDragEnd}
          onTouchStart={handleDragStart}
          onTouchEnd={handleDragEnd}
          className="w-full h-2 opacity-0 absolute appearance-none cursor-pointer"
        />
        <div
          className="flex justify-between text-custom-gray mt-4"
        >
          <span>{formatTime(currentTime)}</span>
          <span>{formatTime(duration)}</span>
        </div>
      </div>

      <div
        className="flex flex-row justify-center items-center gap-10 mt-20">
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
