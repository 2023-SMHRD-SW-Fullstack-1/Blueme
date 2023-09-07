import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Howl } from 'howler';

// import - 플레이어 아이콘
import { ReactComponent as Prev } from "../assets/img/musicPlayer/backward.svg";
import { ReactComponent as Next } from "../assets/img/musicPlayer/forward.svg";
import { ReactComponent as Play } from "../assets/img/musicPlayer/play.svg";
import { ReactComponent as Pause } from "../assets/img/musicPlayer/pause.svg";
import { ReactComponent as Rotate } from "../assets/img/musicPlayer/rotate.svg";

// import - 이미지
import likeEmpty from "../assets/img/likeEmpty.png";
import likeFull from "../assets/img/likeFull.png";
import scroll from "../assets/img/musicPlayer/scrollDown.png";

// import 더미데이터
import dummy from "../dummy/MusicDummy.json";

const MusicPlayer = ({item}) => {
  const navigate = useNavigate();
  const location = useLocation();

  item = location.state?.item;

  let searchParams = new URLSearchParams(location.search);
  let urlParam = searchParams.get("url");
  let url = urlParam.split("/");
  let songId = parseInt(url[url.length - 1]); // 음악아이디
  url.pop(); // 배열의 마지막 요소 제거
  let urlWithoutSongId = url.join("/"); // http://172.30.1.27:8104/music

  
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  
   const [sound, setSound] = useState(null);

    const prevTrack=()=>{
        if(songId-1<0){
            songId=3;
        }
        else{
            songId-=1;
        }
        navigate(`/MusicPlayer?url=${urlWithoutSongId}/${songId}`);
    }

    const nextTrack=()=>{
        if(songId+1>3){
            songId=1;
        }
        else{
            songId+=1;
            
        }
       navigate(`/MusicPlayer?url=${urlWithoutSongId}/${songId}`);
    }

     useEffect(() => {
          url=urlParam.split('/');
          songId=parseInt(url[url.length-1]);
          url.pop();
          urlWithoutSongId=url.join('/');

           // Create a new sound using the URL
           if (sound) sound.unload(); 
           
           const newSound=new Howl({
               src:[`${urlWithoutSongId}/${songId}`],
               format:['mpeg'],
               onload(){
                   setDuration(newSound.duration());
                   setIsPlaying(true);
                   newSound.play();
               },
               onend(){nextTrack();}, // automatically go to next track when finished
               onplay(){setIsPlaying(true);},
               onpause(){setIsPlaying(false);}
           });
   
           setSound(newSound);
   
           return () => {
               if (sound) sound.unload(); // unload the sound when unmounting
           };
     }, [urlParam]);

    const changeCurrentTime=(e)=>{
        let newCurrentTime=e.target.value;
        setCurrentTime(newCurrentTime);
        if(sound){
            sound.seek(newCurrentTime);
        }
    }

  return (
    <div className="flex flex-col items-center justify-center bg-custom-blue text-custom-white h-full">
      <p className="py-[10px]">앨범명 : {dummy[0].album}</p>
      <div>
        <img
          src={dummy[0].coverImage}
          className="w-[300px] h-auto rounded-lg"
        />
        <p className="text-2xl pt-[10px]">곡제목 : {dummy[0].title}</p>
        <p>아티스트명: {dummy[0].artist}</p>
      </div>
      {/* 재생바 */}
      <div className="w-[85%] h-2.5 bg-black rounded-full mt-10">
        <div
          style={{ width: `${(currentTime / duration) * 100}%` }}
          className="h-2 bg-white rounded-full"
        />
         <input
            type="range"
            min={0}
            max={duration || 1}
            value={currentTime}
            onChange={changeCurrentTime}
            className="w-full h-2 opacity-0 absolute appearance-none cursor-pointer"
         />
      </div>
      

      <div className="flex flex-row justify-center items-center gap-10 mt-20">
        <Rotate className="w-[25px] h-auto" />
        <Prev className="w-[40px] h-auto" onClick={prevTrack} />
        {isPlaying ? (
  <Pause
    className="w-[50px] h-auto"
    onClick={() => sound.pause()}
  />
) : (
  <Play
    className="w-[50px] h-auto"
    onClick={() => sound.play()}
  />
)}


        <Next className="w-[40px] h-auto" onClick={nextTrack} />
        <div className="ml-auto">
          <img className="w-[30px] h-auto " src={likeEmpty} />
        </div>
        
      </div>
      <img
        src={scroll}
        onClick={() => navigate(-1)}
        className="w-[40px] h-auto fixed bottom-[15%]"
      />
      {/* <audio ref={audioRef} style={{ display: "none" }} /> */}
      </div>
  );
};

export default MusicPlayer;