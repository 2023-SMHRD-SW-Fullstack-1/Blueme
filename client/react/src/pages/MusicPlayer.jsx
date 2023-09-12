/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 음악 플레이어
*/
import { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { Howl } from "howler";

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
import { log } from "react-modal/lib/helpers/ariaAppHider";

const MusicPlayer = ({item}) => {
  // console.log('mp',item);
  const navigate = useNavigate();
  const location = useLocation();

  // 임의 사용자 user_id
  const userId = 1;

  // URL에서 음악 아이디 추출 
  let searchParams = new URLSearchParams(location.search);
  let urlParam = searchParams.get("url");

  const getSongIdFromUrl = (url) => {
    let parts = url.split("/");
    return parseInt(parts[parts.length - 1]);
  };

  const getUrlWithoutSongId = (url) => {
    let parts = url.split("/");
    parts.pop();
    return parts.join("/");
  };

  let songId = getSongIdFromUrl(urlParam);
  let urlWithoutSongId = getUrlWithoutSongId(urlParam);

  // useState
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [sound, setSound] = useState(null);
    // 재생바 이동 관련 useState
  const [isDragging, setIsDragging] = useState(false);
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
   // 좋아요 버튼 관련
  const [isSaved, setIsSaved] = useState(-1); // 초기 좋아요 상태 불러오기
  const [isLiked, setIsLiked] = useState(isSaved > 0 ? true : false);

  // 서버에서 음악 정보 가져오기
  useEffect(() => {
    const fetchMusicInfo = async () => {
      try {
        const response = await axios.get(`${urlWithoutSongId}/info/${songId}`);
        setMusicInfo({
          album: response.data.album,
          title: response.data.title,
          artist: response.data.artist,
          img: response.data.img
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

  // 음악 파일 불러오기
  useEffect(() => {
    songId = getSongIdFromUrl(urlParam);

    if (sound) sound.unload();

    const newSound = new Howl({
      src: [`${urlWithoutSongId}/${songId}`],
      format: ["mpeg"],
      onload() {
        setDuration(newSound.duration());
        setCurrentTime(0);
        setIsPlaying(true);
        newSound.play();
      },
      onend() {
        if (isRepeatModeRef.current) { // 반복 모드 확인
          newSound.seek(0);
          setCurrentTime(0);
          newSound.play();
        } else {
          nextTrack(); 
        }
      },
      onplay() {
        setIsPlaying(true);
      },
      onpause() {
        setIsPlaying(false);
      },
    });

    setSound(newSound);

    return () => {
      if (sound) sound.unload();
    };
  }, [urlParam]); 

  // 좋아요 상태 확인
  useEffect(() => {
    const fetchLikeStatus = async () => {
      try {
        const response = await axios.post(
          "/likemusics/issave",
          { userId: userId.toString(), musicId: songId.toString() }
        );
        setIsSaved(parseInt(response.data));
        setIsLiked(parseInt(response.data) > 0);
      } catch (error) {
        console.error("좋아요 불러오기 실패", error);
      }
    };
    fetchLikeStatus();
  }, [userId, songId]);

  // 좋아요버튼 누르기
  const toggleLike = async () => {
    try {
      await axios.put("/likemusics/toggleLike", {
        userId: userId,
        musicId: songId,
      });
      setIsLiked(!isLiked);
    } catch (error) {
      console.error("등록 실패", error);
    }
  };

  // 최근 재생 목록 추가
  useEffect(() => {
    const fetchRecent = async () => {
      try {
        await axios.post(
          "/playedmusic/add",
          { userId: userId.toString(), musicId: songId.toString() }
        );
      } catch (error) {
        console.error("최근재생 실패", error);
      }
    };
    fetchRecent();
  }, [userId, songId]);
  
  // 사용자 재생바 조작
  const changeCurrentTime = (e) => {
    let newCurrentTime = e.target.value;
    setCurrentTime(newCurrentTime);
    if (!isDragging && sound) {
      sound.seek(newCurrentTime);
    }
  };

  const handleDragStart = () => {
    setIsDragging(true);
    sound?.pause();
  };

  const handleDragEnd = () => {
    setIsDragging(false);
    if (sound) {
      sound.seek(currentTime);
      sound.play();
    }
  };

  // 음악 자동 재생 중 현재 재생위치 업데이트
  useEffect(() => {
    const intervalID = setInterval(() => {
      if (sound?.playing()) {
        setCurrentTime(sound.seek());
      }
    }, 1000);

    return () => clearInterval(intervalID);
  }, [sound]);

  // 이전곡&다음곡
  const prevTrack = () => {
    if (songId - 1 < 0) {
      songId = 100;
    } else {
      songId -= 1;
    }
    navigate(`/MusicPlayer?url=${urlWithoutSongId}/${songId}`);
  };

  const nextTrack = () => {
    if (songId + 1 > 100) {
      songId = 1;
    } else {
      songId += 1;
    }
    navigate(`/MusicPlayer?url=${urlWithoutSongId}/${songId}`);
  };

  // 재생시간 표시
  const formatTime = (timeInSeconds) => {
    const minutes = Math.floor(timeInSeconds / 60);
    const seconds = Math.floor(timeInSeconds % 60);

    return `0${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  };

  return (
    <div className="flex flex-col items-center justify-center bg-custom-blue text-custom-white h-full ">
      <p className="py-[10px]">{musicInfo.album}</p>
      <div className=" w-[300px]">
        <img
          src={"data:image/;base64," + musicInfo.img}
          className="h-auto rounded-lg"
        />
        <p className="text-2xl pt-[10px] ">{musicInfo.title}</p>
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
        <div className="flex justify-between text-custom-gray mt-4">
          <span>{formatTime(currentTime)}</span>
          <span>{formatTime(duration)}</span>
        </div>
      </div>

      <div className="flex flex-row justify-center items-center gap-10 mt-20">
        <img
          className="w-[25px] h-auto"
          src={isRepeatMode ? rotating : rotate}
          onClick={() => setIsRepeatMode(!isRepeatMode)}
        />
        <Prev className="w-[40px] h-auto" onClick={prevTrack} />
        {isPlaying ? (
          <Pause className="w-[50px] h-auto" onClick={() => sound.pause()} />
        ) : (
          <Play className="w-[50px] h-auto" onClick={() => sound.play()} />
        )}

        <Next className="w-[40px] h-auto" onClick={nextTrack} />
        <div className="ml-auto">
          <button onClick={toggleLike}>
            <img
              className="w-[30px] h-auto"
              src={isLiked ? likeFull : likeEmpty}
              alt="like-button"
            />
          </button>
        </div>
      </div>
      <img
        src={scroll}
        onClick={() => navigate('/library')}
        className="w-[40px] h-auto fixed bottom-[10%]"
      />
    </div>
  );
};

export default MusicPlayer;