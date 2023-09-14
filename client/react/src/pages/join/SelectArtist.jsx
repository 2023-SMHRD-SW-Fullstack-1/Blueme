/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 아티스트 선택 및 아티스트 수정 기능
*/
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios'


const SelectArtist = () => {

  const navigate = useNavigate();
  const [artist, setArtist] = useState([])
  const [checkedState, setCheckedState] = useState([]);
  const [page, setPage] = useState(1); //페이징 관련
  const [artists, setArtists] = useState([])//페이징 관련
  const id = localStorage.getItem('id')

    //아티스트 한 개 선택 시
    const handleOnClick = (artistId) => {
        console.log(artistId);
        if(checkedState.includes(artistId)) {
            setCheckedState((prev) => prev.filter((itemId) => itemId !== artistId));
        } else if(checkedState.length < 2) {
            setCheckedState((prev) => [...prev, artistId]);
        } else {
            // 체크 상태가 이미 두 개인 경우 가장 오래된 체크 상태를 제거하고 새로운 것을 추가
            setCheckedState(([_, ...rest]) => [...rest, artistId]);
        }
    };

    //화면 로딩 시 아티스트 데이터 불러오기
    useEffect(() => {
        axios.get("http://172.30.1.45:8104/Artistrecommend")
        .then((res) => {
            console.log(res);
            setArtist(res.data)
        })
        .catch((err) => console.log(err))
    }, [])

    //회원가입 시 아티스트 선택(2개)
    const handleSelect = () => {
        if(checkedState.length === 2) {
          localStorage.setItem('selectArtist1', checkedState[0])
          localStorage.setItem('selectArtist2', checkedState[1])
          // localStorage.setItem('selectGenre', JSON.stringify(checkedState));
          const requestData = {artistIds : checkedState ,favChecklistId : id}
          console.log(requestData);
          axios.post("http://172.30.1.45:8104/SaveFavArtist",requestData)
          .then((res) => {
              if(res.data > 0) {
                navigate('/JoinComplete')
              }else if(res.data == -1) {
                alert('저장되지 않았습니다. 다시 선택해주세요.')
                navigate('./Artistrecommend')
              }   
              console.log(res)
          }).catch((err) => console.log(err))
      } else {
          alert('선호하는 아티스트 2명을 선택해주세요.')
          navigate('/Artistrecommend')
      }
    };
    console.log(checkedState);

    //아티스트 수정
    const handleUpdate = () => {
    // localStorage.setItem('selectGenre1', checkedState[0])
    // localStorage.setItem('selectGenre2', checkedState[1])
    };
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tight h-auto text-custom-white p-3">
      <h3 className="text-3xl pt-[80px]">당신이 좋아하는 아티스트는?</h3>
      <div className="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5 mt-8">
      {artist && artist.map((artist, artistId, artistFilePath) => (
          <button
            key={artist.artistFilePath}
            className="relative flex flex-col items-center space-y-1 mb-5 justify-center"
            onClick={()=> handleOnClick(artist.artistFilePath)}
          >
            <img
              src={"data:image/;base64," + artist.img}
              alt="genre img"
              className="rounded-lg w-[180px] h-[175px] h-auto object-cover blur-[1.5px]"
            />
            <p className="absolute text-2xl">{artist.artistName}</p>
            {checkedState.includes(artist.artistFilePath) && (
              <span className="absolute top-[25%] left-[40%] text-7xl font-bold text-black">
                ✔
              </span>
            )}
          </button>
        ))}
      </div>
      {localStorage.getItem('email') === null ?
      <button
      onClick={handleSelect}
      className="
        mt-5
        w-full
        px-3 h-10 relative 
        bg-[#221a38]  
        rounded-lg border border-soild border-[#fdfdfd]
        text-custom-white
        text-[16px]
        mb-[80px]"
        
    >
      선택하기
    </button> : <button
          onClick={handleUpdate}
          className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[16px]
            mb-[80px]"
        >
          수정하기
        </button>}
     <div/>
    </div>
  );
};

export default SelectArtist;
