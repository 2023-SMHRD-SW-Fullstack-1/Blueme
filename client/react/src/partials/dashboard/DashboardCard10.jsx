import React, {useState, useEffect} from "react";
import axios from "axios";


function DashboardCard10() {
  const [customers, setCustomers] = useState([]);
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/user/allusers`)
        .then(res => {
          console.log(res);
          setCustomers(res.data);
        })
        .catch(err => console.log(err))
  },[])

  return (
    <div className="col-span-full  bg-white dark:bg-slate-800 shadow-lg rounded-sm border border-slate-200 dark:border-slate-700 mt-[100px] mx-5 w-full">
      <header className="px-5 py-4 border-b border-slate-100 dark:border-slate-700">
        <h2 className="font-semibold text-slate-800 dark:text-slate-100">
          회원
        </h2>
      </header>
      <div className="p-3">
        {/* Table */}
        <div className="overflow-x-auto">
          <table className="table-auto w-full">
            {/* Table header */}
            <thead className="text-xs font-semibold uppercase text-slate-400 dark:text-slate-500 bg-slate-50 dark:bg-slate-700 dark:bg-opacity-50">
              <tr>
                <th className="p-2 whitespace-nowrap">
                  <div className="font-semibold text-left">닉네임</div>
                </th>
                <th className="p-2 whitespace-nowrap">
                  <div className="font-semibold text-left">이메일</div>
                </th>
                <th className="p-2 whitespace-nowrap">
                  <div className="font-semibold text-left">플랫폼</div>
                </th>
                <th className="p-2 whitespace-nowrap">
                  <div className="font-semibold text-center">역할</div>
                </th>
                <th className="p-2 whitespace-nowrap">
                  <div className="font-semibold text-center">가입시간</div>
                </th>
              </tr>
            </thead>
            {/* Table body */}
            <tbody className="text-sm divide-y divide-slate-100 dark:divide-slate-700">
              {customers && customers.map((customer) => {
                return (
                  <tr key={customer.id}>
                    <td className="p-2 whitespace-nowrap">
                      <div className="flex items-center">
                        <div className="font-medium text-slate-800 dark:text-slate-100">
                          {customer.nickname}
                        </div>
                      </div>
                    </td>
                    <td className="p-2 whitespace-nowrap">
                      <div className="text-left">{customer.email}</div>
                    </td>
                    <td className="p-2 whitespace-nowrap">
                      <div className="text-left font-medium text-green-500">
                        {customer.platformType}
                      </div>
                    </td>
                    <td className="p-2 whitespace-nowrap">
                      <div className="text-center">
                        {customer.role}
                      </div>
                    </td>
                    <td className="p-2 whitespace-nowrap">
                      <div className="text-center">
                        {new Intl.DateTimeFormat('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' }).format(new Date(customer.createdAt))}
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default DashboardCard10;
