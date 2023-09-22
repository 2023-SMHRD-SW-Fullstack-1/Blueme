/*
작성자: 이유영
날짜(수정포함): 2023-09-21
설명: 장르 컴포넌트
*/
import React from 'react'

const Genre = ({genre,checkedState,genreId}) => {


  
  return (
    <div className='relative flex flex-col items-center space-y-1 mb-5 justify-center'>
        <img
              src={"data:image/;base64," + genre.img}
              alt="genre img"
              className="rounded-lg w-[180px] h-[175px] object-cover blur-[1.5px]"
            />
            <p className="absolute text-2xl">{genre.name}</p>
            {checkedState.includes(parseInt(genreId)+1) && (
              <span className="absolute top-[25%] left-[40%] text-7xl font-bold text-black">
                ✔
              </span>
            )}
    </div>
  )
}

export default Genre