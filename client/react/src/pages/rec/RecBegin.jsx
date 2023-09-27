import React from 'react'
import { Link, useNavigate } from 'react-router-dom';
// import - 이미지
import watch from '../../assets/img/recPages/pinkwatch.svg'
import gpt from '../../assets/img/recPages/gpt.svg'
import '../../App.css'
import { useSelector } from 'react-redux';

/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 추천 시작 화면 및 토스트창 구현
*/

const RecBegin = () => {
  const navigate = useNavigate()
  const user = useSelector(state => state.memberReducer.user)
  const isLogin = user.isLogin
  // console.log('header',user);

  //3초 로딩 함수
  const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').classList.remove("reveal")//토스트 창 소멸
      navigate("/Login");
    }, 3000);// 원하는 시간 ms단위로 적어주기
  };

  const recBegin = () => {
    if(isLogin) {
      navigate('/LoadData')
    }else {
      document.getElementById('toast-warning').classList.add("reveal")//토스트 창 생성
      timeout()
    } 
  }

  return (
    // 추천 페이지 소개
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white text-center flex flex-col min-h-screen justify-center items-center'>
      <p className='text-xl tracking-tighter'>내 건강 데이터를 기반으로 
      <br /> ChatGPT가 추천해주는 음악,
      <br /> 궁금하지 않나요?</p>

      {/* ChatGPT와 웨어러블 이미지 */}
      <div className='flex flex-row py-5'>
        <img src={watch} className='m-3 w-[55px] h-[55px] ' />
        <img src={gpt} className='m-3 w-[55px] h-[55px]' />
      </div>
     

    {/* 시작하기 버튼  */}
      <button className='border border-soild border-#FDFDFD rounded-xl p-3 tracking-tight leading-[1.45]'
              onClick={recBegin}>시작하기</button>
    
     {/* 토스트 창 띄우기 */}
     <div id="toast-warning" className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
    <div className="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 text-orange-500 bg-orange-100 rounded-lg dark:bg-orange-700 dark:text-orange-200">
        <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
            <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM10 15a1 1 0 1 1 0-2 1 1 0 0 1 0 2Zm1-4a1 1 0 0 1-2 0V6a1 1 0 0 1 2 0v5Z"/>
        </svg>
        <span className="sr-only">Warning icon</span>
    </div>
    <div className="ml-3 font-normal">로그인 후 이용해주세요.</div>
    </div>
    
</div>
    
  );
};

export default RecBegin

