/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 미니플레이어
*/

import { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { Howl } from "howler";

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

const MiniPlayer = ({ item }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();

  // useState
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [isPlaying, setIsPlaying] = useState(null);
  const [sound, setSound] = useState(null);
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
  const musicIds = useSelector((state) => state.musicIds);
  // console.log("miniplayer musicids:", musicIds);
  const [currentSongIndex, setCurrentSongIndex] = useState(-1);
  const songId = useSelector((state) => state.currentSongId);
  const playingStatus = useSelector((state) => state.playingStatus);
  // 임의 사용자 user_id
  const userId = 1;

  // 서버에서 음악 정보 가져오기
  useEffect(() => {
    const fetchMusicInfo = async () => {
      try {
        const response = await axios.get(`/music/info/${songId}`);
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
  }, [songId]); // songId 변경 시마다 재실행

  // 한곡반복 Ref
  useEffect(() => {
    isRepeatModeRef.current = isRepeatMode;
  }, [isRepeatMode]);

  

  // 이전곡&다음곡
  useEffect(() => {
    // console.log('1. songid',songId);
    const index = musicIds.indexOf(Number(songId));
    // console.log('2. index:', index);
    setCurrentSongIndex(index);
    setCurrentSongId(Number(songId));
    // console.log('3. currentsongid',currentSongId);
  }, [musicIds, songId]);

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

// 재생/일시정지 확인
  useEffect(() => {
     if (sound) {
     const isCurrentlyPlaying = sound.playing();

     setIsPlaying(isCurrentlyPlaying);

    // 재생 상태 업데이트
     dispatch(setPlayingStatus(isCurrentlyPlaying));
     }
   }, [sound]);

  // 최근 재생 목록 추가
  useEffect(() => {
    const fetchRecent = async () => {
      try {
        await axios.post("/playedmusic/add", {
          userId: userId.toString(),
          musicId: songId.toString(),
        });
      } catch (error) {
        console.error("최근재생 실패", error);
      }
    };
    fetchRecent();
  }, [userId, songId]);

  // 리덕스에 저장됐는지 확인
    useEffect(() => {
       console.log('isPlaying:', isPlaying);
       console.log('playingStatus:', playingStatus);
     }, [playingStatus]);


  return (
    <div className="flex items-center bg-custom-blue text-custom-white fixed bottom-[7.5%] w-full h-[8%] px-6">
      <img
        src={"data:image/;base64," + musicInfo.img}
        className="h-[80%] rounded-lg"
      />
      <div className="flex flex-col ml-4">
        <p className="lg:text-4xl">{musicInfo.title}</p>
        <p className="lg:text-xl">{musicInfo.artist}</p>
      </div>

      <div className="flex flex-row gap-3 ml-auto">
        <Prev className="w-[30px] m h-auto" onClick={prevTrack} />
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
        <Next className="w-[30px] h-auto" onClick={nextTrack} />
      </div>
    </div>
  );
};

export default MiniPlayer;
