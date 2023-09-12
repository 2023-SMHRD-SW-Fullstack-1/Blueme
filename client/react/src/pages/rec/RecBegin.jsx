import React from 'react'
import { Link } from 'react-router-dom';
// import - 이미지
import watch from '../../assets/img/recPages/pinkwatch.svg'
import gpt from '../../assets/img/recPages/gpt.svg'

/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 추천 소개 페이지
*/

const RecBegin = () => {

  return (
    // 추천 페이지 소개
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white text-center flex flex-col min-h-screen justify-center items-center'>
      <p className='text-2xl font-semibold tracking-tighter'>내 건강 데이터를 기반으로 
      <br /> ChatGPT가 추천해주는 음악,
      <br /> 궁금하지 않나요?</p>

      {/* ChatGPT와 웨어러블 이미지 */}
      <div className='flex flex-row py-5'>
        <img src={watch} className='m-3 w-[70px] h-[70px] ' />
        <img src={gpt} className='m-3 w-[70px] h-[70px]' />
      </div>

    {/* 시작하기 버튼  */}
      <Link to='/LoadData' className='border border-soild border-#FDFDFD rounded-xl p-3 tracking-tighter leading-[1.45] '><button>시작하기</button></Link>
    </div>
    
  );
};

export default RecBegin

