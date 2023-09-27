/*
작성자: 이지희
날짜(수정포함): 2023-09-24
설명: 미니플레이어, ProgressBar추가(0924)
*/

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";

// import - 플레이어 아이콘
import { ReactComponent as Prev } from "../../assets/img/musicPlayer/backward.svg";
import { ReactComponent as Next } from "../../assets/img/musicPlayer/forward.svg";
import { ReactComponent as Play } from "../../assets/img/musicPlayer/play.svg";
import { ReactComponent as Pause } from "../../assets/img/musicPlayer/pause.svg";

// Redux
import { setCurrentSongId, setPlayingStatus, setIsMusicPlayer } from "../../store/music/musicActions";

// component
import ProgressBar from "../../components/music/ProgressBar";

const MiniPlayer = () => {
  const duration = useSelector((state) => state.musicReducer.duration);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 음악 관련 정보
  const [musicInfo, setMusicInfo] = useState({
    album: "",
    title: "",
    artist: "",
    img: "",
  });

  // 음악 재생 인덱스 (리덕스 활용)
  const musicIds = useSelector((state) => state.musicReducer.musicIds);
  const [currentSongIndex, setCurrentSongIndex] = useState(-1);
  const currentSongId = useSelector(
    (state) => state.musicReducer.currentSongId
  );
  const playingStatus = useSelector(
    (state) => state.musicReducer.playingStatus
  );
  const currentTime = useSelector((state) => state.musicReducer.currentTime);

  // 사용자 id
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  useEffect(() => {
    dispatch(setIsMusicPlayer(false));
  }, []);

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
        console.error("음악 불러오기 실패", error);
      }
    };
    fetchMusicInfo();
  }, [currentSongId]); // songId 변경 시마다 재실행

  // 이전곡&다음곡
  useEffect(() => {
    const index = musicIds.indexOf(Number(currentSongId));
    setCurrentSongIndex(index);
    setCurrentSongId(Number(currentSongId));
  }, [musicIds, currentSongId]);

  const prevTrack = () => {
    let prevIndex = currentSongIndex - 1;

    if (prevIndex < 0) {
      prevIndex = musicIds.length - 1;
    } else if (prevIndex === musicIds.length - 1) {
      prevIndex = 0;
    }

    const prevSongId = musicIds[prevIndex];

    dispatch(setCurrentSongId(prevSongId));
  };

  const nextTrack = () => {
    let nextIndex = currentSongIndex + 1;

    if (nextIndex >= musicIds.length) {
      nextIndex = 0;
    }

    const nextSongId = musicIds[nextIndex];

    dispatch(setCurrentSongId(nextSongId));
  };

  // 최근 재생 목록 추가
  useEffect(() => {
    const fetchRecent = async () => {
      try {
        await axios.post(`${process.env.REACT_APP_API_BASE_URL}/playedmusic/add`, {
          userId: userId.toString(),
          musicId: currentSongId.toString(),
        });
      } catch (error) {
        console.error("최근재생 실패", error);
      }
    };
    fetchRecent();
  }, [userId, currentSongId]);

  // Mini -> MusicPlayer 이동
  const handleMusicClick = () => {
    navigate(`/MusicPlayer/${currentSongId}`);
  };

  return (
    currentSongId && (
      <div className="flex items-center bg-custom-blue text-custom-white fixed bottom-16 w-full h-[8%] xs:px-3 lg:px-6 z-50">
        <div onClick={handleMusicClick} className="h-[80%]">
          <div className="h-[100%] flex flex-row">
            <img src={"data:image/;base64," + musicInfo.img} className="h-[100%] rounded-lg" alt="" />

            <div className="flex flex-col lg:ml-4 xs:ml-3 justify-center">
              <p
                className="md:text-lg font-semibold xs:text-sm xs:pb-1 overflow-hidden overflow-ellipsis whitespace-nowrap"
                style={{ maxWidth: "190px" }}
              >
                {musicInfo.title}
              </p>
              <p className="lg:text-md xs:text-xs">{musicInfo.artist}</p>
            </div>
          </div>
        </div>
        <div className="xl:flex xl:justify-center xl:w-[75%] xl:pb-[10px] xl:mt-[-30px] ml-auto mr-auto">
          <ProgressBar currentTime={currentTime} duration={duration} playingStatus={playingStatus} />
        </div>
        <div className="flex flex-row xs:gap-3 lg:gap-6 ml-auto lg:mr-[30px]">
          <Prev className="xs:w-[25px] lg:w-[40px] m h-auto" onClick={prevTrack} />
          {playingStatus ? (
            <Pause
              className="xs:w-[30px] lg:w-[40px] h-auto"
              onClick={() => {
                dispatch(setPlayingStatus(false));
              }}
              alt="Pause"
            />
          ) : (
            <Play
              className="xs:w-[30px] lg:w-[40px] h-auto"
              onClick={() => {
                dispatch(setPlayingStatus(true));
              }}
              alt="Pause"
            />
          )}
          <Next className="xs:w-[25px] lg:w-[40px] h-auto" onClick={nextTrack} />
        </div>
      </div>
    )
  );
};

export default MiniPlayer;
