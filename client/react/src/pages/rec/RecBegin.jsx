import React from 'react'
import { Link } from 'react-router-dom';
// import - 이미지
import watch from '../../assets/img/recPages/pinkwatch.svg'
import gpt from '../../assets/img/recPages/gpt.svg'
const RecBegin = () => {
  return (
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white text-center flex flex-col min-h-screen justify-center items-center'>
      <p className='text-2xl font-semibold tracking-tighter'>내 건강 데이터를 기반으로 
      <br /> ChatGPT가 추천해주는 음악,
      <br /> 궁금하지 않나요?</p>
    <div className='flex flex-row py-5'>
      <img src={watch} className='m-3 w-[70px] h-[70px] ' />
      <img src={gpt} className='m-3 w-[70px] h-[70px]' />
    </div>
    <Link to='/LoadData' className='border-2 border-soild border-#FDFDFD rounded-xl p-3 tracking-tighter leading-[1.45] '><button>시작하기</button></Link>
    </div>
    
  );
};

export default RecBegin

