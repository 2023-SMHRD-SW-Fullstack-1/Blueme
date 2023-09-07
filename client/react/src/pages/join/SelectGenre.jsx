// SelectArtist.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const SelectGenre = () => {
  const dummySingers = [
    {
      id: 1,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/1.jpg`,
    },
    {
      id: 2,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/2.jpg`,
    },
    {
      id: 3,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/3.jpg`,
    },
    {
      id: 4,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/4.jpg`,
    },
    {
      id: 5,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/5.jpg`,
    },
    {
      id: 6,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/6.jpg`,
    },
    {
      id: 7,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/7.jpg`,
    },
    {
      id: 8,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/8.jpg`,
    },
    {
      id: 9,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/9.jpg`,
    },
    {
      id: 10,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/10.jpg`,
    },
    {
      id: 11,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/11.jpg`,
    },
    {
      id: 12,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/12.jpg`,
    },
  ];

  const [checkedState, setCheckedState] = useState(new Array(dummySingers.length).fill(false));
  const navigate = useNavigate();

  const handleOnClick = (index) => {
    const checkedCount = checkedState.filter(Boolean).length;
    if (checkedCount < 2 || checkedState[index]) {
      setCheckedState(checkedState.map((item, idx) => (idx === index ? !item : item)));
    }
  };

  const handleOnSubmit = () => {
    const selectedArtists = dummySingers.filter((_, index) => checkedState[index]);

    navigate("/MyPage", { state: { selectedArtists } });
  };

  return (
    <div className="h-full flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow ">
      <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10 pb-10 pt-10">당신이 좋아하는 장르는?</h3>
      <div class="grid grid-cols-3 sm:grid-cols-3 md:grid-cols-gap-x-6 gap-y-4 gap-x-5">
        {dummySingers.map((singer, i) => (
          <button key={i} class="relative flex flex-col items-center space-y-1 mb-10 " onClick={() => handleOnClick(i)}>
            <img src={singer.image} alt="" class="rounded-lg w-[100px] h-auto object-cover" />

            {checkedState[i] && <span className="absolute top-[30%] left-[40%] text-xl font-bold">✔</span>}
          </button>
        ))}
      </div>
      <div className="w-full h-auto rounded-lg p-4 ">
        <button
          onClick={handleOnSubmit}
          className="
              mt-5
              w-full
              px-3 h-auto relative 
              bg-[#221a38]
              rounded-lg border border-soild border-[#fdfdfd]
              text-custom-white
              mb-10"
        >
          수정하기
        </button>
      </div>
    </div>
  );
};

export default SelectGenre;

// const handleOnClick = (index) => {
//     const checkedCount = checkedState.filter(Boolean).length;
//     if (checkedCount < 2 || checkedState[index]) {
//     setCheckedState(checkedState.map((item, idx) => (idx === index ? !item : item)));
//     }
//     //
