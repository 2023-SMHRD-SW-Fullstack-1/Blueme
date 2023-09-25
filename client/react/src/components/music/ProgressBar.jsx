import React from 'react';
import { useDispatch } from "react-redux";
import { setCurrentTime, setDraggingStatus, setPlayingStatus } from "../../store/music/musicActions";

const ProgressBar = ({ currentTime, duration, playingStatus }) => {
  const dispatch = useDispatch();

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

 

   return (
      <div className="w-[85%] h-2.5 bg-black rounded-full mt-10 relative">
        <div
          style={{ width: `${(currentTime / duration) * 100}%` }}
          className="h-2 bg-white rounded-full absolute"
          alt=""
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
      </div>
   );
}

export default ProgressBar;