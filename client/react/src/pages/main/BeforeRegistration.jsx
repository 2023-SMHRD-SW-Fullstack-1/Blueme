
/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 디자인 수정
*/

import React from 'react'
import watch from '../../assets/img/watch.png'
import { Link } from 'react-router-dom'

const MainWatch = () => {
  return (
    <div>
      <div className='flex flex-col justify-center items-center p-5 mt-3 relative'>
        <img src={watch}
          alt='워치 이미지'
          class="object-cover blur-sm w-[150px] h-[210px]"/>
        <Link to='/RecBegin' className="transform -translate-y-0 absolute">
          <button className='text-xl text-custom-recappdes font-semibold tracking-tighter'>스마트 워치를 등록해주세요.</button>
        </Link>
      </div>    
    </div>
  )
}

export default MainWatch