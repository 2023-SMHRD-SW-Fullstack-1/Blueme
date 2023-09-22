/*
작성자: 이유영
날짜(수정포함): 2023-09-20
설명:추천 플레이리스트 최신 10개 컴포넌트
*/

import React from "react";



const SingleMusic = ({ item }) => {




  return (
    <div>
        <div className="flex flex-col justify-center items-center lg:w-[600px] mt-8 lg:pl-[40px] ">
          <img
              src={"data:image/;base64,"+item.img}
              alt="album cover"
              className="w-[250px] h-auto rounded-lg"
              />
            <p className="tracking-tight text-sm text-center mt-2  w-[200px] ">
            {item.recMusiclistTitle}
            </p>
        </div>

    </div>
  );
};

export default SingleMusic;