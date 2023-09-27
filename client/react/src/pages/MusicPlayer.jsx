/*
작성자: 이지희
날짜(수정포함): 2023-09-15
설명: 음악 플레이어 수정, Heart 컴포넌트 분리(0925)
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
import rotate from "../assets/img/musicPlayer/rotate.png";
import rotating from "../assets/img/musicPlayer/rotating.png";

// 컴포넌트
import ProgressBar from "../components/music/ProgressBar";
import Heart from "../components/Library/Heart";

// Redux - 음악 관련
import {
  setCurrentSongId,
  setPlayingStatus,
  setRepeatMode,
  setIsMusicPlayer,
} from "../store/music/musicActions";

// component

const MusicPlayer = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // 사용자 id
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  // 음악 관련 정보
  const [musicInfo, setMusicInfo] = useState({
    album: "",
    title: "",
    artist: "",
    img: "",
  });

  // 음악 재생 인덱스 (리덕스 활용)
  const musicIds = useSelector((state) => state.musicReducer.musicIds);
  const currentSongId = useSelector(
    (state) => state.musicReducer.currentSongId
  );
  const playingStatus = useSelector(
    (state) => state.musicReducer.playingStatus
  );
  const currentTime = useSelector((state) => state.musicReducer.currentTime);
  const duration = useSelector((state) => state.musicReducer.duration);
  const repeatMode = useSelector((state) => state.musicReducer.repeatMode);

  // 뮤직플레이어 접속 여부 확인 (음악 자동재생용)
  useEffect(() => {
    dispatch(setIsMusicPlayer(true));
  }, [dispatch]);

  // 서버에서 음악 정보 가져오기
  useEffect(() => {
    const fetchMusicInfo = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/music/info/${currentSongId}`);
        setMusicInfo({
          album: response.data.album,
          title: response.data.title,
          artist: response.data.artist,
          img: response.data.img,
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

  // 재생시간 표시
  const formatTime = (timeInSeconds) => {
    timeInSeconds = Number(timeInSeconds);

    const minutes = Math.floor(timeInSeconds / 60);
    const seconds = Math.floor(timeInSeconds % 60);

    return `${minutes < 10 ? "0" : ""}${minutes}:${
      seconds < 10 ? "0" : ""
    }${seconds}`;
  };

  return (
    <div className="flex flex-col items-center justify-center bg-custom-blue text-custom-white h-full pb-[24px]">
      <p className="py-[10px] text-custom-gray">{musicInfo.album}</p>
      <div className="flex flex-col justify-center w-[300px]">
        <img
          src={"data:image/;base64," + musicInfo.img}
          alt=""
          className="h-auto rounded-lg"
        />
        <p
          className={`text-xl md:text-2xl font-semibold pt-[10px] ${
            musicInfo.title.length > 20 ? "marquee" : ""
          }`}
        >
          {musicInfo.title}
        </p>{" "}
        <p>{musicInfo.artist}</p>
      </div>
      {/* 재생바 */}
      <ProgressBar
        currentTime={currentTime}
        duration={duration}
        playingStatus={playingStatus}
      />
      <div
        className="flex justify-between text-custom-gray mt-3"
        style={{ width: "81%" }}
      >
        <span>{formatTime(currentTime)}</span>
        <span>{formatTime(duration)}</span>
      </div>
      {/* 재생제어 */}
      <div className="flex flex-row justify-center items-center gap-12 mt-4">
        <img
          className="lg:w-[25px] h-auto xs:w-[20px] mr-auto"
          alt=""
          src={repeatMode ? rotating : rotate}
          onClick={() => dispatch(setRepeatMode(!repeatMode))}
        />
        <div className="flex flex-row xs:gap-6">
          <Prev
            className="lg:w-[50px] xs:w-[35px] h-auto"
            onClick={prevTrack}
          />
          {playingStatus ? (
            <Pause
              className="lg:w-[50px] xs:w-[40px] h-auto"
              onClick={() => {
                dispatch(setPlayingStatus(false));
              }}
            />
          ) : (
            <Play
              className="lg:w-[50px] xs:w-[40px] h-auto"
              onClick={() => {
                dispatch(setPlayingStatus(true));
              }}
            />
          )}
          <Next
            className="lg:w-[50px] xs:w-[35px] h-auto"
            onClick={nextTrack}
          />
        </div>
        <Heart item={currentSongId} />
      </div>
    </div>
  );
};

export default MusicPlayer;
