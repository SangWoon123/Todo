import { defineStore } from "pinia";
import axios from "axios";

export const userTaskStore = defineStore("task", {
  state: () => ({
    tasks: [],
    historys: {},
  }),

  getters: {},

  actions: {
    async addTask(newTask) {
      const accessToken = localStorage.getItem("accessToken");
      const data = {
        content: newTask.trim(),
        description: "",
        done: false,
      };
      const url = "http://localhost:8080/task";

      if (newTask.trim() !== "") {
        await axios
          .post(url, data, {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          })
          .then((response) => {
            this.tasks.push(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    async deleteTask(id) {
      const accessToken = localStorage.getItem("accessToken");
      const url = `http://localhost:8080/task/${id}`;

      try {
        await axios.delete(url, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        // API 호출이 성공하면, 작업 리스트에서 해당 작업을 삭제합니다.
        this.tasks = this.tasks.filter((task) => task.id !== id);
      } catch (error) {
        console.log(error);
      }
    },
    async getAllTasks() {
      const accessToken = localStorage.getItem("accessToken");
      const url = "http://localhost:8080/task/today";

      try {
        const response = await axios.get(url, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });

        this.tasks = response.data; // 서버에서 받은 task들을 tasks에 저장
      } catch (error) {
        console.error(error);
      }
    },
    async updateDescription({ id, description }) {
      const task = this.tasks.find((task) => task.id === id);
      if (task) {
        task.description = description;
        const url = `http://localhost:8080/task/${id}`; // 실제 API 엔드포인트로 변경해주세요.
        const accessToken = localStorage.getItem("accessToken");

        const data = {
          id: id,
          description: description,
        };

        try {
          const response = await axios.patch(url, data, {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          console.log(response.data);
        } catch (error) {
          console.error(error);
        }
      }
    },
    async deleteDescription(id) {
      const task = this.tasks.find((task) => task.id === id);
      if (task) {
        task.description = "";
        const url = `http://localhost:8080/task/${id}`; // 실제 API 엔드포인트로 변경해주세요.
        const accessToken = localStorage.getItem("accessToken");

        const data = {
          id: id,
          description: "",
        };

        try {
          const response = await axios.patch(url, data, {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          console.log(response.data);
        } catch (error) {
          console.error(error);
        }
      }
    },
    async updateComplete(id) {
      const task = this.tasks.find((task) => task.id === id);
      const url = `http://localhost:8080/task/complete/${id}`;
      const accessToken = localStorage.getItem("accessToken");

      const currentTime = new Date();
      task.completeTime = currentTime;

      await axios
        .get(url, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          console.log(`${task.content} Task complete`);
        });
    },
    async getHistory() {
      const url = "http://localhost:8080/history";
      const accessToken = localStorage.getItem("accessToken");

      await axios
        .get(url, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          const datas = res.data.data.map((history) => history); // proxy로 감싸진 historyResponse(*참고 swagger-ui)
          for (let i in datas) {
            this.historys[i] = datas[i]; // proxy로 감싸여진 데이터를 벗겨내어 historys에 저장
          }

        });
    },
  },
});
