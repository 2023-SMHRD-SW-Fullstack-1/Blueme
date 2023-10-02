import React, {useState, useEffect} from "react";
import axios from "axios";

function DashboardCard07() {

  const [musics, setMusics] = useState();

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/top10`)
      .then((response) => {
        console.log(response);
        setMusics(response.data);
      }).catch(err => console.log(err))
  }, [])
  

  return (
    <div className="col-span-full xl:col-span-8 bg-white dark:bg-slate-800 shadow-lg rounded-sm border border-slate-200 dark:border-slate-700">
      <header className="px-5 py-4 border-b border-slate-100 dark:border-slate-700">
        <h2 className="font-semibold text-slate-800 dark:text-slate-100">
          Top Musics
        </h2>
      </header>
      <div className="p-3">
        {/* Table */}
        <div className="overflow-x-auto">
          <table className="table-auto w-full dark:text-slate-300">
            {/* Table header */}
            <thead className="text-xs uppercase text-slate-400 dark:text-slate-500 bg-slate-50 dark:bg-slate-700 dark:bg-opacity-50 rounded-sm">
              <tr>
                <th className="p-2">
                  <div className="font-semibold text-left">제목</div>
                </th>
                <th className="p-2">
                  <div className="font-semibold text-center">조회수</div>
                </th>
                <th className="p-2">
                  <div className="font-semibold text-center">가수</div>
                </th>
                <th className="p-2">
                  <div className="font-semibold text-center">장르</div>
                </th>
              </tr>
            </thead>
            {/* Table body */}
            <tbody className="text-sm font-medium divide-y divide-slate-100 dark:divide-slate-700">
              {/* Row */}
              {musics && musics.map((music) => {
                return <tr>
                  <td className="p-2">
                    <div className="flex items-center">
                      <img src={"data:image/;base64," + music.img} className="shrink-0 mr-2 sm:mr-3 w-[36px] h-[36px] rounded-full" alt=""/>
                      <div className="text-slate-800 dark:text-slate-100">
                        {music.title}
                      </div>
                    </div>
                  </td>
                  <td className="p-2">
                    <div className="text-center text-emerald-500">{music.hit}</div>
                  </td>
                  <td className="p-2">
                    <div className="text-center">{music.artist}</div>
                  </td>
                  <td className="p-2">
                    <div className="text-center">{music.genre}</div>
                  </td>
                </tr>
              })}
              
              
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default DashboardCard07;
