import React from 'react'
import watchLogin from '../../assets/img/watchlogin.png'
import watchHeartRate from '../../assets/img/watchheartrate.png'
import { Swiper, SwiperSlide } from 'swiper/react';
import { Link } from "react-router-dom";

const RecAppDes = () => {

  return (
    <div className='bg-custom-blue text-custom-recappdes p-3 h-full '>
        <br/><br/><br/>
        {/* 갤럭시 워치에 대한 앱 설명 */}
        <div className='mt-3'>
          <ul>
            <li className='text-left indent-1 text-xl font-semibold tracking-tighter'>• 갤럭시 워치의 경우</li>
            <ol className='text-left indent-3 tracking-tighter mt-3 leading-loose'>
                <li>1. 갤럭시 워치에서 구글 스토어를 실행합니다.</li>
                <li>2. 검색창에 Blueme를 검색하고 다운로드 받습니다.</li>
                <li>3. 앱 설치가 완료되면 자사 로그인을 진행합니다.</li>
                <li>4. 데이터가 워치에 보여지면 전송 버튼을 클릭합니다.</li>
            </ol>
          </ul>
        </div>

        <Swiper slidesPerView={2} className='mt-10'>
            <SwiperSlide >
                <img src={watchLogin}
                alt='워치 이미지'
                class="object-cover w-[180px] h-[230px] "/>
            </SwiperSlide> 
            <SwiperSlide>
                <img src={watchHeartRate}
                alt='워치 이미지'
                class="object-cover w-[180px] h-[225px] "/>
            </SwiperSlide> 
        </Swiper>
        
        {/* 샤오미 워치에 대한 앱 설명 */}
        <div className='mt-8'>
        <ul>
            <li className='text-left indent-1 text-xl font-semibold tracking-tighter'>• 샤오미 미워치의 경우</li>
            <ol className='text-left indent-3 tracking-tighter mt-3 leading-loose'>
                <li>1. 미워치에서 샤오미웨어를 실행합니다.</li>
                <li>2. 검색창에 Blueme를 검색하고 다운로드 받습니다.</li>
                <li>3. 앱 설치가 완료되면 자사 로그인을 진행합니다.</li>
                <li>4. 데이터가 워치에 보여지면 전송 버튼을 클릭합니다.</li>
            </ol>
          </ul>     
        </div>
        <div className='text-right p-3 mt-5'>
            <Link to='/PlaylistRename'><span>SKIP</span></Link>
        </div>
    </div>
  )
}

export default RecAppDes