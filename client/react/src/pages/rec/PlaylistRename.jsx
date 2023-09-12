import React from 'react'
import { Link } from 'react-router-dom';

/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 추천 받은 음악 제목 수정
*/

const PlaylistRename = () => {
    
    // 현재 날짜와 시간 구하기
    const todayTime = () => {
        let now = new Date();
        let todayYear = now.getFullYear();
        let todayMonth = now.getMonth()+1;
        let todayDate = now.getDate();
        let hours = now.getHours();
        let minutes = now.getMinutes();

        return todayYear + '년' + todayMonth + '월' + todayDate + '일' + hours + '시' + minutes +'분 당신을 위한 음악'
    }

  return (
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full'>
    <br/><br/><br/>
    {/* 추천 리스트 제목 */}
      <div className='mt-52 text-center'>
          <h1 className='text-xl font-semibold tracking-tight'>추천 플레이리스트의 제목을 정해주세요.</h1>
      </div>

    {/* 제목에 기본값으로 현재 날짜와 시간 넣어주기 */}
      <div className='mt-12'>
      <input
        type="email"
        className="focus:border-custom-white pl-2 w-full border border-soild rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.35] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
        placeholder= {todayTime()}
      />
      </div>
      
      {/* 저장과 취소 버튼 */}
      <div className='flex justify-end space-x-2'>
        <div className="h-[35px] w-[53px] mt-5 border border-soild border-#FDFDFD] 
        rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
            <Link to='/RecPlayList'><button>저장</button></Link>
        </div>
        <div className="h-[35px] w-[53px] mt-5 border border-soild border-#FDFDFD] 
        rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
             <Link to='/RecPlayList'><button>취소</button></Link>
        </div>
      </div>
      




      

  </div>
  )
}

export default PlaylistRename