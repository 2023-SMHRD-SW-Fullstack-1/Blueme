import React from 'react'
import watch from '../../assets/img/watch.png'
import { Link } from 'react-router-dom'

const MainWatch = () => {
  return (
    <div>
        <div className='flex justify-center items-center p-5 mt-3'>
          <img src={watch}
          alt='워치 이미지'
          class="object-cover blur-sm w-[150px] h-[210px] "/>
          <div className='absolute'>
            <Link to='/RecBegin'>
              <button className='text-xl text-custom-recappdes font-semibold tracking-tighter'>스마트 워치를 등록해주세요.</button>
            </Link>
          </div>
        </div>    
    </div>
  )
}

export default MainWatch