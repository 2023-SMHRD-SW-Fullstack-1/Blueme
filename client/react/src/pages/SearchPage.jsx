
import React, { useEffect, useState } from 'react'
import Search from "../assets/img/search.png";
import RecentSearch from '../components/search/RecentSearch';
import axios from 'axios';
import { useSelector } from "react-redux";

/*
작성자 : 김혁
작성일 : 2023-09-15
설명   : 음악검색 페이지
*/

const SearchPage = () => {
  const [searchList, setSearchList] = useState("");
  const [recentList, setRecentList] = useState("");
  const [searchKeyword, setSearchKeyword] = useState("");

  // 로그인한 userId 값
  // 사용자 id
const user = useSelector(state => state.memberReducer.user)
const logginedUserId = user.id

  // 최근 검색목록 조회하는 함수
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/search/${logginedUserId}`)
      .then((res) => {
        setRecentList(res.data);
      }).catch(err => console.log(err)); 
  },[])

  

  // 검색값 db가져오는 함수
  const handleSearchKeyword = (e) => {
    if(e.target.value !== "") {
      axios.get(`${process.env.REACT_APP_API_BASE_URL}/search/music/${e.target.value}`)
      .then((res) => {
          setSearchList(res.data);
        }).catch(err => console.log(err));
    }else{
      setSearchList("");
    }
    setSearchKeyword(e.target.value);
  }
  return (
    <div className='overflow-auto mb-16 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full pb-20 hide-scrollbar'>
      <div className="py-2 flex justify-center pt-10 mt-10">
        <label className="relative block w-full max-w-xl ">
          <span className="sr-only">Search</span>
          <span className="absolute inset-y-0 left-0 flex items-center pl-2">
            <img src={Search} className="h-[15px] w-[15px] ml-1" alt=""/>
          </span>
          <input className="bg-[#404752] placeholder:italic placeholder:text-slate-400 block w-full rounded-md py-2 pl-9 pr-3 shadow-sm focus:outline-none focus:border-sky-500 focus:ring-white focus:ring sm:text-sm"
            placeholder="어떤 음악을 듣고싶으세요?" 
            type="text" 
            name="search"
            onChange={handleSearchKeyword}
          />
        </label>
      </div>
      <div className='2xl:px-[25%] xl:px-[10%] lg:px-[8%] md:px-[5%]'>
        {searchList && searchList.length && 
        <div>
          {searchList && searchList.length > 0 && searchList.map((item) => <>
              <RecentSearch item = {item} key={item.searchId}/>
            </>)}
        </div>
        }
        {searchKeyword !== "" &&  searchList.length ===0 && <div className='h-full flex justify-center items-center flex-col pb-[100px]'>
            <p className='text-2xl mb-4 p-3'>"{searchKeyword}"과(와) 일치하는 결과 없음.</p>
            <p>철자가 맞는지 확인하거나 다른 키워드를 사용해주세요.</p>
          </div>
        }
        {searchKeyword === "" && 
          <div>
            <h1 className='mt-2 '>최근검색</h1>
            <div>
              {recentList && recentList.length > 0 && recentList.map((item) => <>
                <RecentSearch item = {item} key={item.searchId}/>
              </>)}
            </div>
          </div>
        }
      </div>
      
      

    </div>
  )
}

export default SearchPage