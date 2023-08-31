import React from "react";
import { Link } from "react-router-dom";

const Artistrecommend = () => {
  return (
    <div className="flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
      <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10 pb-10 pt-10">당신이 좋아하는 가수는?</h3>
      <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-2 gap-x-6 gap-y-1">
        {Array(6)
          .fill()
          .map((_, i) => (
            <button key={i} class="flex flex-col items-center space-y-1 mb-10">
              <img
                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                alt=""
                class="rounded-full w-[150px] h-[150px] object-cover"
              />
              <h5 class="font-semibold">Bernard</h5>
            </button>
          ))}
      </div>
      <Link to="/Main" className="mb-10">
        결과는?
      </Link>
    </div>
  );
};

export default Artistrecommend;
