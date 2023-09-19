/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: 미니플레이어
*/

import { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

// import - 플레이어 아이콘
import { ReactComponent as Prev } from "../../assets/img/musicPlayer/backward.svg";
import { ReactComponent as Next } from "../../assets/img/musicPlayer/forward.svg";
import { ReactComponent as Play } from "../../assets/img/musicPlayer/play.svg";
import { ReactComponent as Pause } from "../../assets/img/musicPlayer/pause.svg";

// Redux
import {
  setCurrentSongId,
  setPlayingStatus,
} from "../../store/music/musicActions";

const MiniPlayer = () => {
  const dispatch = useDispatch();

  // 한곡반복
  const [isRepeatMode, setIsRepeatMode] = useState(false);
  const isRepeatModeRef = useRef(isRepeatMode); // Ref 생성
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
  const currentSongId = useSelector((state) => state.musicReducer.currentSongId);
  const playingStatus = useSelector((state) => state.musicReducer.playingStatus);
  
  // 사용자 id
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id

  // 서버에서 음악 정보 가져오기
  useEffect(() => {
    const fetchMusicInfo = async () => {
      try {
        const response = await axios.get(`/music/info/${currentSongId}`);
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

  // 한곡반복 Ref
  useEffect(() => {
    isRepeatModeRef.current = isRepeatMode;
  }, [isRepeatMode]);

  

  // 이전곡&다음곡
  useEffect(() => {
    // console.log('1. songid',songId);
    const index = musicIds.indexOf(Number(currentSongId));
    // console.log('2. index:', index);
    setCurrentSongIndex(index);
    setCurrentSongId(Number(currentSongId));
    // console.log('3. currentsongid',currentSongId);
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
        await axios.post("/playedmusic/add", {
          userId: userId.toString(),
          musicId: currentSongId.toString(),
        });
      } catch (error) {
        console.error("최근재생 실패", error);
      }
    };
    fetchRecent();
  }, [userId, currentSongId]);



  return (
    <div className="flex items-center bg-custom-blue text-custom-white fixed bottom-[7.5%] w-full h-[8%] px-6">
      <Link to={`/MusicPlayer/${currentSongId}`} className="h-[80%]">
      <div className="h-[95%] flex flex-row">
      <img
        src={"data:image/;base64," + musicInfo.img}
        className="h-[100%] rounded-lg"
        alt=""
      />
      
      <div className="flex flex-col ml-4 justify-center">
        <p className="lg:text-2xl sm:font-semibold">{musicInfo.title}</p>
        <p className="lg:text-lg sm:text-sm">{musicInfo.artist}</p>
      </div>
      </div>
      </Link>

      <div className="flex flex-row sm:gap-3 lg:gap-5 ml-auto">
        <Prev className="sm:w-[30px] lg:w-[40px] m h-auto" onClick={prevTrack} />
        {playingStatus ? (
          <Pause
            className="sm:w-[30px] lg:w-[40px] h-auto"
            onClick={() => {
              dispatch(setPlayingStatus(false));
            }}
          />
        ) : (
          <Play
            className="sm:w-[30px] lg:w-[40px] h-auto"
            onClick={() => {
              dispatch(setPlayingStatus(true));
            }}
          />
        )}
        <Next className="sm:w-[30px] lg:w-[40px] h-auto" onClick={nextTrack} />
      </div>
    </div>
  );
};

export default MiniPlayer;
