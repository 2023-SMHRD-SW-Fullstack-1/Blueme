/*
작성자: 이지희
날짜(수정포함): 2023-09-12
설명: 음악 플레이어 - 리덕스 활용 추가
*/
import { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
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

// Redux
import {
  setCurrentSongId,
  setPlayingStatus,
  setShowMiniPlayer
} from "../store/music/musicActions";

const MusicPlayer = ({ item }) => {

  // 임의 사용자 user_id
  const userId = 1;

  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();

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
  // 음악 재생 인덱스 (리덕스 활용)
  const musicIds = useSelector((state) => state.musicIds);
  // console.log("musicplayer musicids:", musicIds);
  const [currentSongIndex, setCurrentSongIndex] = useState(-1);
  const currentSongId = useSelector((state) => state.currentSongId);
  
  // URL에서 음악 아이디 추출
  let params = useParams();
  let songId = params.id;

  // 풀스크린 설정
  const showMiniPlayer = useSelector(state => state.showMiniPlayer);

  useEffect(() => {
    
    if(location.pathname.includes("/MusicPlayer")){
      dispatch(setShowMiniPlayer(false));
    } else {
      dispatch(setShowMiniPlayer(true));
    }
  }, [location]);



  // 음악 정보 & 음원 불러오기
  useEffect(() => {
    const fetchMusicInfoAndPlay = async () => {
      try {
        // 음악 정보 가져오기
        const response = await axios.get(`/music/info/${songId}`);
        setMusicInfo({
          album: response.data.album,
          title: response.data.title,
          artist: response.data.artist,
          img: response.data.img,
        });

        // 이전 사운드 언로드
        if (sound) sound.unload();

        // 새로운 사운드 로드 및 재생
        const newSound = new Howl({
          src: [`/music/${songId}`],
          format: ["mpeg"],
          onload() {
            setDuration(newSound.duration());
            setCurrentTime(0);
            setIsPlaying(true);
            newSound.play();
            dispatch(setCurrentSongId(songId));
          },
          onend() {
            if (isRepeatModeRef.current) {
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
      } catch (error) {
        console.error("음악 불러오기 실패", error);
      }
    };

    fetchMusicInfoAndPlay();

    return () => {
      if (sound) sound.unload();
    };
  },  [songId, currentSongIndex, musicIds]);

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
    }

    const prevSongId = musicIds[prevIndex];

    dispatch(setCurrentSongId(prevSongId));

    navigate(`/MusicPlayer/${prevSongId}`);
  };

  const nextTrack = () => {
    let nextIndex = currentSongIndex + 1;

    if (nextIndex >= musicIds.length) {
      nextIndex = 0;
    }
    const nextSongId = musicIds[nextIndex];
    dispatch(setCurrentSongId(nextSongId));

    navigate(`/MusicPlayer/${nextSongId}`);
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

  useEffect(() => {
    const fetchLikeStatusAndRecent = async () => {
      try {
        // 좋아요 상태 확인
        const likeRequest = axios.post("/likemusics/issave", {
          userId: userId.toString(),
          musicId: songId.toString(),
        });

        // 최근 재생목록 추가
        const recentRequest = axios.post("/playedmusic/add", {
          userId: userId.toString(),
          musicId: songId.toString(),
        });

        const [likeResponse] = await Promise.all([likeRequest, recentRequest]);

        setIsSaved(parseInt(likeResponse.data));
        setIsLiked(parseInt(likeResponse.data) > 0);
      } catch (error) {
        console.error("데이터 가져오기 실패", error);
      }
    };

    fetchLikeStatusAndRecent();
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

  // 사용자 재생바 조작
  const changeCurrentTime = (e) => {
    let newCurrentTime = e.target.value;
    setCurrentTime(newCurrentTime);
    if (sound) {
      sound.seek(newCurrentTime);
      if (!isDragging) {
        sound.play();
      }
    }
  };

  const handleDragStart = () => {
    setIsDragging(true);
  };

  const handleDragEnd = () => {
    setIsDragging(false);
    if (sound) {
      sound.play();
    }
  };

  // 음악 자동 재생 중 현재 재생위치 업데이트
  useEffect(() => {
    const intervalID = setInterval(() => {
      if (sound?.playing()) {
        const currentTime = sound.seek();
        setCurrentTime(currentTime);
        if (currentTime >= duration) {
          nextTrack();
        }
      }
    }, 1000);

    return () => clearInterval(intervalID);
  }, [sound, duration]);

  // 재생시간 표시
  const formatTime = (timeInSeconds) => {
    const minutes = Math.floor(timeInSeconds / 60);
    const seconds = Math.floor(timeInSeconds % 60);

    return `0${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  };

  return (
    <div className={showMiniPlayer ? 'mini-player' : 'full-screen-player'}>
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
        <div className= { showMiniPlayer ? "hidden" :"flex justify-between text-custom-gray mt-4"}>
          <span>{formatTime(currentTime)}</span>
          <span>{formatTime(duration)}</span>
        </div>
      </div>

      <div className={ showMiniPlayer ? "flex items-center gap-5 ml-auto" :"flex flex-row justify-center items-center gap-10 mt-20"}>
        <img
          className={ showMiniPlayer ? "hidden" : "w-[25px] h-auto"}
          src={isRepeatMode ? rotating : rotate}
          onClick={() => setIsRepeatMode(!isRepeatMode)}
          alt=""
        />
        <Prev className={ showMiniPlayer ? "w-[20px] h-auto" : "w-[50px] h-auto"} onClick={prevTrack} />
        {isPlaying ? (
          <Pause className={ showMiniPlayer ? "w-[30px] h-auto" : "w-[50px] h-auto"} onClick={() => sound.pause()} />
        ) : (
          <Play className={ showMiniPlayer ? "w-[30px] h-auto" : "w-[50px] h-auto"} onClick={() => sound.play()} />
        )}

        <Next className={ showMiniPlayer ? "w-[20px] h-auto" : "w-[50px] h-auto"} onClick={nextTrack} />
        <div className="ml-auto">
          <button onClick={toggleLike}>
            <img
              className={showMiniPlayer ? "hidden" : "w-[30px] h-auto"}
              src={isLiked ? likeFull : likeEmpty}
              alt="like-button"
            />
          </button>
        </div>
      </div>
      <img
        src={scroll}
        onClick={() => navigate("/library")}
        className={showMiniPlayer ? "hidden" : "w-[40px] h-auto fixed bottom-[10%]"}
      />
    </div>
  );
};

export default MusicPlayer;
