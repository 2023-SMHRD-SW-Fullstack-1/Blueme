import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const SelectArtist = () => {
  const [checkedState, setCheckedState] = useState(new Array(12).fill(false));
  const navigate = useNavigate();

  const handleOnClick = (index) => {
    const checkedCount = checkedState.filter(Boolean).length;
    if (checkedCount < 2 || checkedState[index]) {
      setCheckedState(checkedState.map((item, idx) => (idx === index ? !item : item)));
    }
  };

  const handleOnSubmit = () => {
    const checkedIndices = checkedState.reduce((indices, item, index) => [...indices, ...(item ? [index] : [])], []);

    navigate("/MyPage", { state: { selectedArtists: checkedIndices } });
  };
  return (
    <div className="h-fullflex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
      <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10 pb-10 pt-10">당신이 좋아하는 가수는?</h3>
      <div class="grid grid-cols-3 sm:grid-cols-3 md:grid-cols-gap-x-6 gap-y-4 gap-x-5">
        {Array(12)
          .fill()
          .map((_, i) => (
            <button
              key={i}
              class="relative flex flex-col items-center space-y-1 mb-10"
              onClick={() => handleOnClick(i)}
            >
              <img
                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                alt=""
                class="rounded-lg w-[100px] h-auto object-cover"
              />

              {checkedState[i] && <span className="absolute top-[30%] left-[40%] text-xl font-bold">✔</span>}
            </button>
          ))}
      </div>
      <div className="w-full h-auto rounded-lg p-4">
        <button
          onClick={handleOnSubmit}
          className="
            mt-5
            w-full
            px-3 h-auto relative 
            bg-[#221a38]
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white"
        >
          수정하기
        </button>
      </div>
    </div>
  );
};

export default SelectArtist;
