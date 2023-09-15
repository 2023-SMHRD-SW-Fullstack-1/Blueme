import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';
import React from 'react'
import { useNavigate } from "react-router-dom";

const RecentSearch = ({item}) => {
  const navigate = useNavigate();
  let userId = 1;
  // 최근검색 등록
  const uploadRecentMusic = () => {
    axios.post('http://172.30.1.27:8104/search', {
      userId: userId,
      musicId: item.musicId
    })
    .then((response) => {
        navigate(`/MusicPlayer/${item.musicId}`);
    })
    .catch((error) => {
        console.error(error);
    });
  }
  return (
      <div className='w-full pr-3 flex justify-between items-center gap-x-4' onClick={uploadRecentMusic}>
        <img
            className='rounded-full m-2 w-14 flex-none'
            src={"data:image/;base64," + item.img} 
            alt="no img"
            />
        <div className='grow'>
          <p>{item.title}</p>
          <p className="text-[8px] text-[#949494]">{item.artist}</p>
        </div>
        <FontAwesomeIcon className='flex-none' icon={faArrowRight} style={{color: "#ebebeb",}} />
      </div>
    
  )
}

export default RecentSearch