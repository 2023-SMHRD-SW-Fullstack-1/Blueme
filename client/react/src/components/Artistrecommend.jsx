import React from "react";

const Artistrecommend = () => {
  return (
    <div className="flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
      <h3 class="font-bold text-4xl font-sans mb-10">당신이 좋아하는 가수는?</h3>
      <div class="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-10">
        {Array(6)
          .fill()
          .map((_, i) => (
            <button key={i} class="flex flex-col items-center space-y-4">
              <img
                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                alt=""
                class="rounded-full w-[150px] h-[150px] object-cover"
              />
              <h5 class="font-semibold">Bernard</h5>
            </button>
          ))}
      </div>
    </div>
  );
};

export default Artistrecommend;
