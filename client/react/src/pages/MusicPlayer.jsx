import { useEffect, useState } from "react";
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

const MusicPlayer = () => {
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
    // 음악 아이디 제외한 URL 추출
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
  // 한곡 반복 모드 useState
  const [isRepeatMode, setIsRepeatMode] = useState(false);
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
          img: response.data.img,
        });
      } catch (error) {
        console.error("음악 불러오기 실패", error);
      }
    };
    fetchMusicInfo();
  }, [songId]); // songId 변경 시마다 재실행

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
        nextTrack();
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
          "http://172.30.1.27:8104/likemusics/issave",
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
      await axios.put("http://172.30.1.27:8104/likemusics/toggleLike", {
        userId: userId,
        musicId: songId,
      });
      setIsLiked(!isLiked);
    } catch (error) {
      console.error("등록 실패", error);
    }
  };

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
      songId = 3;
    } else {
      songId -= 1;
    }
    navigate(`/MusicPlayer?url=${urlWithoutSongId}/${songId}`);
  };

  const nextTrack = () => {
    if (songId + 1 > 3) {
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
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-lightblue p-3 h-full font-semibold tracking-tighter ">
      <img
        src={scroll}
        onClick={() => navigate(-1)}
        className="w-[40px] h-auto fixed top-[30px]"
      />
      <div className="mt-[200px] flex flex-col items-center">
      <p className="mb-8 text-center">{musicInfo.album}</p>
        <img
          src={"data:image/;base64," + musicInfo.img}
          className="h-[250px] w-[250px] rounded-xl"
        /></div>
        <div className="ml-[70px] mt-[13px] text-custom-white">
          <p className="text-[23px]">{musicInfo.title}</p>
          <p className="text-[16px] mt-2">{musicInfo.artist}</p>

        </div>
      {/* 재생바 */}
      <div className="w-[100%] h-3 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 rounded mt-[60px] relative">
        <div
          style={{ width: `${(currentTime / duration) * 100}%` }}
          className="h-1 bg-white rounded-full absolute text-sm"
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
          <span className="mt-3 text-[13px] text-#e2e2e2e">{formatTime(currentTime)}</span>
          <span className="mt-3 text-[13px] text-#e2e2e2e">{formatTime(duration)}</span>
        </div>
      </div>

      <div className="flex flex-row justify-center items-center gap-[60px] mt-[60px]">
        <img
          className="w-[20px] h-auto"
          src={isRepeatMode ? rotating : rotate}
          onClick={() => setIsRepeatMode(!isRepeatMode)}
        />
        <Prev className="w-[35px] h-auto" onClick={prevTrack} />
        {isPlaying ? (
          <Pause className="w-[30px] h-auto " onClick={() => sound.pause()} />
        ) : (
          <Play className="w-[30px] h-auto" onClick={() => sound.play()} />
        )}

        <Next className="w-[30px] h-auto" onClick={nextTrack} />

          <button onClick={toggleLike}>
            <img
              className="w-[25px] h-[25px]"
              src={isLiked ? likeFull : likeEmpty}
              alt="like-button"
            />
          </button>

      </div>
     
    </div>
  );
};

export default MusicPlayer;