<template>
  <div class="container" v-if="!doneVoting">
    <div v-if="currentOption && currentOption.description
    " class="option-display">
      <div class="header">
        <h2>{{ movie_data.title ?? currentOption.description }}
        </h2>

        <div class="vote-avrg" v-if="session.isFilmSession">
          {{ movie_data.vote_average }} <svg width="46" height="46" fill="none" stroke="currentColor"
            stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg">
            <path d="m12 2 3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z">
            </path>
          </svg>
        </div>
      </div>
      <span>
        {{ currentOptionIndex }} / {{ options.length }}
      </span>

      <img v-if="sessionData.isFilmSession && movie_img" :src="movie_img" alt="movie poster" />

      <div class="controls">
        <button class="button --secondary" @click="dislike">Don't Like <svg width="46" height="46" fill="none"
            stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M10 15v4a3 3 0 0 0 3 3l4-9V2H5.72a2 2 0 0 0-2 1.7l-1.38 9a2 2 0 0 0 2 2.3zm7-13h2.67A2.31 2.31 0 0 1 22 4v7a2.31 2.31 0 0 1-2.33 2H17">
            </path>
          </svg></button>
        <button class="button" @click="like">Like <svg width="46" height="46" fill="none" stroke="currentColor"
            stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3">
            </path>
          </svg></button>
      </div>
    </div>
    <div v-else>
      <button class="button" @click="startSession">Start Voting</button>
    </div>



  </div>
  <div v-if="doneVoting" class="container">
    <h2>Great Job!</h2>
    <p>Waiting for the session to end. You will be redirected to the results page as soon as all votes are in.
    </p>
    <b>If you are the session owner, you can need to end the session manually.
    </b>
  </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue';
import { Session, Option, Movie } from '../types';
import { getAPIUrl } from '../utils';

export default defineComponent({
  name: 'DisplayOptions',
  props: {
    session: {
      type: Object as () => Session,
      required: true,
    },
  },

  data() {
    return {
      sessionIDredirect: '',
      error: '',
      isSessionOwner: false,
      sessionData: {} as Session,
      options: [] as Option[],
      currentOptionIndex: 0,
      currentOption: {} as Option,
      movie_data: {} as Movie,
      movie_img: '',

      doneVoting: false,
    };
  },
  mounted() {
    this.sessionData = this.session;
    this.options = this.sessionData.options;

    if (this.sessionData.isActive == false) {
      this.$router.push('/results/' + this.sessionData.sessionID);
    }

  },
  methods: {

    async waitUntilSessionEnds() {
      // eslint-disable-next-line no-constant-condition
      while (true) {
        let url = getAPIUrl() + '/getSessionInfo?session=' + this.sessionData.sessionID;
        let response = await fetch(url);
        let data = await response.json();
        console.log("Session data: ", data);

        if (data.isActive == false) {
          this.$router.push('/results/' + this.sessionData.sessionID);
          break;
        }
        await new Promise(resolve => setTimeout(resolve, 1000));
      }
    },

    async startSession() {

      if (Array.isArray(this.options) && this.options.length > 0) {
        this.currentOption = this.options[0];
        if (this.sessionData.isFilmSession) {
          await this.getMovie(this.currentOption.optionID);
        }
      } else {
        alert('No options available');
      }
    },


    async getMovie(movie_id: number): Promise<void> {
      if (!this.sessionData.isFilmSession || !movie_id) {
        console.log('Not applicable for fetching movie image.');
        return;
      }
      let url = getAPIUrl() + '/getFilm?filmID=' + movie_id;
      let response = await fetch(url);
      let data = await response.json();
      this.movie_data = data;
      this.movie_img = `https://image.tmdb.org/t/p/w500${data.poster_path}`;
    },


    async like() {
      let url = getAPIUrl() + '/newVote?session=' + this.sessionData.sessionID + '&&option=' + this.currentOption.optionID;
      await fetch(url);

      if (this.currentOptionIndex < this.options.length - 1) {
        this.currentOptionIndex += 1;
        this.currentOption = this.options[this.currentOptionIndex];
        if (this.sessionData.isFilmSession) {
          await this.getMovie(this.currentOption.optionID);
        }
      } else {
        this.doneVoting = true;
        await this.waitUntilSessionEnds();
      }
    },

    async dislike() {
      if (this.currentOptionIndex < this.options.length - 1) {
        this.currentOptionIndex += 1;
        this.currentOption = this.options[this.currentOptionIndex];
        if (this.sessionData.isFilmSession) {
          await this.getMovie(this.currentOption.optionID);
        }
      } else {
        this.doneVoting = true;
        await this.waitUntilSessionEnds();
      }
    }
  },
});
</script>
<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 80vh;
}

.option-display {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
  font-size: 24px;
}

.controls {
  display: flex;
  justify-content: space-between;
  min-width: 300px;
  gap: 10px;
}

.button {
  padding: 10px 20px;
  border: none;
  cursor: pointer;

  &.--secondary {
    background-color: #eee;
  }
}

img {
  height: auto;

  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);

  @media (max-width: 600px) {
    width: 100px;
  }
}

.vote-avrg {
  display: flex;
  align-items: center;
  gap: 5px;

  svg {
    width: 20px;
    height: 20px;
    fill: var(--primary);
    stroke: var(--primary);
  }
}

.header {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
