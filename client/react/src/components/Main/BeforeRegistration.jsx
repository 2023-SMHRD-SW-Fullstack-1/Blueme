
/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 디자인 수정
*/
/*
작성자: 이유영
날짜(수정포함): 2023-09-08
설명: 워치 화면 구현
*/

import React, { useEffect } from 'react'
import watch from '../../assets/img/watch.png'
import { Link } from 'react-router-dom'


const MainWatch = ({setIsLoading}) => {

  useEffect(() => {
    setIsLoading(false)
  })

  return (
    <div>
      <div className='flex flex-col justify-center items-center p-5 mt-3 relative'>
        <img src={watch}
          alt='워치 이미지'
          className="object-cover blur-sm w-[150px] h-[210px] "/>
          <div className='absolute'>
            <Link to='/RecBegin'>
              <button className='text-xl text-custom-recappdes tracking-tight'>스마트 워치를 등록해주세요.</button>
            </Link>
          </div>
        </div>    
    </div>
  )
}

export default MainWatch