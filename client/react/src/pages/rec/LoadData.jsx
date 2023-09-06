import React from 'react'
import { Link } from 'react-router-dom';

const LoadData = () => {
  return (
    <div  className='bg-custom-blue text-custom-white text-center flex flex-col min-h-screen justify-center items-center text-3xl'>
      내 데이터 불러오는 중
      <Link to='/LoadDataCompl'><button>다음</button></Link>
      </div>
  )
}

export default LoadData