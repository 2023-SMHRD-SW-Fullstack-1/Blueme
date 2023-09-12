import React, { useState } from 'react'
import watchLogin from '../../assets/img/watchlogin.png'
import watchHeartRate from '../../assets/img/watchheartrate.png'
import { Swiper, SwiperSlide } from 'swiper/react';
import { Link } from "react-router-dom";

/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 워치 앱 사용 설명서
*/

const RecAppDes = () => {

  const [modalIsOpen, setModalIsOpen] = useState(false) //모달 열림 여부

  //모달창 열리면 true로 바꿔주는 함수
  const handleTransport = () => {
    setModalIsOpen(true);
  };


  return (
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-lightblue p-3 h-full '>
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

        {/* 위치 이미지  */}
        <Swiper slidesPerView={2} className='mt-10'>
            <SwiperSlide >
                <img src={watchLogin}
                alt='워치 이미지'
                class="object-cover w-[200px] h-[230px] "/>
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
          <button onClick={handleTransport}>SKIP</button>
        
        {/* 데이터 전송 여부 판단하는 모달찬 => Skip 클릭 시 열림 */}
        <div
        id="popup-modal"
        tabIndex="-1"
        className={`fixed top-0 left-0 right-0 bottom-0 z-50 p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] max-h-full ${
          modalIsOpen ? "" : "hidden"
        } flex items-center justify-center`}
      >
        <div class="flex justify-center w-full max-h-full">
          <div class=" bg-custom-blue border rounded-lg shadow dark:bg-gray-700">
            <button
              type="button"
              class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ml-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
              data-modal-hide="popup-modal"
            >
              <svg
                class="w-3 h-3"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 14 14"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
              </svg>
              <span class="sr-only">Close modal</span>
            </button>
            <div class="p-6 text-center">
              <svg
                class="mx-auto mb-4 text-gray-400 w-40 h-10 dark:text-gray-200"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 20 20"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                />
              </svg>
              <h3 class="mb-5 text-lg font-semibold text-gray-400 dark:text-gray-400">데이터를 전송하셨습니까?</h3>
              <Link to='/LoadDataCompl'><button
                data-modal-hide="popup-modal"
                type="button"
                class="text-white bg-gray-500 hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2"
              >네 전송했어요.</button></Link>
              <Link to='/RecBegin'><button
                data-modal-hide="popup-modal"
                type="button"
                class="text-white bg-gray-500 hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2"
              >아니요 안할래요.</button></Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RecAppDes