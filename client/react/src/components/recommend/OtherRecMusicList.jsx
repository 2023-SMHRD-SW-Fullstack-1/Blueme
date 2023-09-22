/*
작성자: 이유영
날짜(수정포함): 2023-09-20
설명:추천 플레이리스트 최신 10개 컴포넌트
*/

import React, { useState } from "react";
import Play from '../../assets/img/play.png'



const OtherRecMusicList = ({ item }) => {

  const [isMouseOver, setIsMouseOver] = useState(false)

  const handleMouseOver = () => {
    setIsMouseOver(true)
  }

  const handleMouseOut = () => {
    setIsMouseOver(false)
  }


  return (
    <div>
       <div className="flex flex-col justify-center items-center mt-8 lg:pl-[15px] hover:shadow-inner">
          <div className="relative w-[180px] h-auto rounded-lg" onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
            <img src={"data:image/;base64,"+ item.img} alt="album cover"/>
            {isMouseOver && 
              <>
                <img src={Play} alt="noimg" className="absolute top-0 right-0 w-[30px]" />
                <p className="absolute bottom-0 left-0 w-full h-[105px] overflow-hidden  text-white bg-black bg-opacity-50 p-2">{item.recMusiclistReason}</p>
              </>
            }
          </div>
          <p className="tracking-tight text-normal text-center mt-3 w-[180px] ">
            {item.recMusiclistTitle}
          </p>
        </div>
    </div>

  );
};

export default OtherRecMusicList;