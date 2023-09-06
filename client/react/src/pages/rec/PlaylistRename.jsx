import React from 'react'

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
    <div className='bg-custom-blue text-custom-white p-3 h-full'>
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
        <div className="h-9 w-12 mt-5 border-2 border-soild border-#FDFDFD] 
        rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
            <button>저장</button>
        </div>
        <div className="h-9 w-12 mt-5 border-2 border-soild border-#FDFDFD] 
        rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
            <button>취소</button>
        </div>
      </div>
      




      

  </div>
  )
}

export default PlaylistRename