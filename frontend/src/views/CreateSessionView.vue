<template>
  <div class="home">
    <div v-if="currentStep == 1" class="step">
      <h2>This session will be about</h2>
      <div class="option" :class="{ '--selected': selectedOption === 'movies' }" @click="selectOption('movies')">
        movies
      </div>
      <div class="option" :class="{ '--selected': selectedOption === 'custom' }" @click="selectOption('custom')">
        custom text
      </div>
    </div>


    <div v-if="currentStep == 2 && selectedOption === 'movies'" class="step">
      <h2>Select Genres</h2>
      <p>What genres should we include into selection</p>
      <div class="container">
        <div v-for="(genreId, genreName) in genresMap" :key="genreId">
          <div class="small-option" :class="{ '--selected': selectedGenres.includes(genreId) }"
            @click="toggleGenre(genreId)">
            {{ genreName }}
          </div>
        </div>
      </div>


    </div>

    <div v-if="currentStep == 2 && selectedOption === 'custom'" class="step">
      <h2>Add Your Custom Selection Options</h2>
      <p>What custom options should we include into selection?</p>

      <div class="container">
        <div v-for="(option, index) in customOptions" :key="index" class="custom-option">
          <span class="small-option --selected">{{ option }}
            <svg @click="removeCustomOption(index)" width="46" height="46" fill="none" stroke="currentColor"
              stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg">
              <path d="M12 2a10 10 0 1 0 0 20 10 10 0 1 0 0-20z"></path>
              <path d="m15 9-6 6"></path>
              <path d="m9 9 6 6"></path>
            </svg>
          </span>
        </div>
      </div>

      <div class="add-option">
        <input v-model="newOption" placeholder="Enter a new option" @keyup.enter="addCustomOption" />
      </div>
    </div>

    <div class="controls">
      <button class="button --secondary" @click="currentStep--" v-if="currentStep > 1">
        Back
      </button>
      <button class="button" :class="{ '--disabled': !selectedOption }" @click="incrementStepOrCreateSession">
        {{
      currentStep === 1 ? 'Next' : 'Create session'
    }}
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { getAPIUrl } from '../utils';

export default defineComponent({
  name: 'CreateSessionView',
  components: {},
  data() {
    return {
      sessionIDredirect: '',
      error: '',
      selectedGenres: [] as number[],
      currentStep: 1,
      selectedOption: '',
      genresMap: {
        Action: 28,
        Adventure: 12,
        Animation: 16,
        Comedy: 35,
        Crime: 80,
        Documentary: 99,
        Drama: 18,
        Family: 10751,
        Fantasy: 14,
        History: 36,
        Horror: 27,
        Music: 10402,
        Mystery: 9648,
        Romance: 10749,
        'Science Fiction': 878,
        'TV Movie': 10770,
        Thriller: 53,
        War: 10752,
        Western: 37,
      },
      customOptions: [] as string[],
      newOption: '',

    };
  },
  methods: {

    //a function to save the session id to the local storage as owns_session : 1234
    saveSessionID(sessionID: string) {
      localStorage.setItem('owns_session', sessionID);
    },

    incrementStepOrCreateSession() {
      if (this.currentStep === 1) {
        this.currentStep++;
      } else {
        if (this.selectedOption === 'movies') {
          this.createFilmSession();
        } else {
          this.createCustomSession();
        }
      }
    },

    addCustomOption() {
      if (this.newOption) {
        this.customOptions.push(this.newOption);
        this.newOption = '';
      }
    },
    removeCustomOption(index: number) {
      this.customOptions.splice(index, 1);
    },
    async createFilmSession() {
      let url = getAPIUrl() + `/createFilmSession?genres=${this.selectedGenres.join(',')}`;
      let response = await fetch(url);
      let data = await response.json();

      if (data.sessionID) {
        this.saveSessionID(data.sessionID);
        this.$router.push(`/join/${data.sessionID}`);
      } else {
        this.error = data.error;
      }
    },

    async createCustomSession() {
      let url = getAPIUrl() + `/createGenericSession?options=${this.customOptions.join(',')}`;
      let response = await fetch(url);
      let data = await response.json();
      if (data.sessionID) {
        this.saveSessionID(data.sessionID);
        this.$router.push(`/join/${data.sessionID}`);
      } else {
        this.error = data.error;
      }
    },
    selectOption(option: string) {
      this.selectedOption = option; // Add this method to update the selected option
    },
    toggleGenre(genre: number) {
      if (this.selectedGenres.includes(genre)) {
        this.selectedGenres = this.selectedGenres.filter((g) => g !== genre);
      } else {
        this.selectedGenres.push(genre);
      }
    },
  },
  computed: {

  },
});
</script>

<style lang="scss">
.home {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;

  height: 100vh;
  gap: 1.5rem;
  padding: 2rem 0;
}

.step {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.2rem;
}

.controls {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.error {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;
}

.container {
  display: flex;
  align-items: center;
  justify-content: center;

  flex-wrap: wrap;
  gap: 10px;
  max-width: 600px;
}

.small-option {
  color: #BFC3CF;
  font-family: Inter;
  font-size: 14.984px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;

  display: flex;
  padding: 12px 24px;
  align-items: flex-start;
  gap: 5px;

  border-radius: 100px;
  border: 2px solid #DEE0E7;
  justify-content: space-between;
  cursor: pointer;

  svg {
    cursor: pointer;
    width: 20px;
    height: 20px;
  }

  &.--selected {
    color: #EA5B6E;
    border: 2px solid #EA5B6E;
  }
}
</style>