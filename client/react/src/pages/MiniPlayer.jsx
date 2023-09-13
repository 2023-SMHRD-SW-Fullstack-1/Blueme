import React from 'react';
import { useNavigate } from 'react-router-dom';
import { ReactComponent as Play } from "../assets/img/musicPlayer/play.svg";
import { ReactComponent as Pause } from "../assets/img/musicPlayer/pause.svg";

function MiniPLayer() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/MusicPlayer');
  }

  return (
    <div onClick={handleClick} className="fixed bottom-[8%] w-full h-[6%] bg-custom-gray">
      {/* <Play className='w-10 h-auto' /> */}
    </div>
  );
}

export default MiniPLayer;