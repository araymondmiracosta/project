<template>
    <div>
        <div v-if="error" class="error">{{ error }}</div>
        <div class="home" v-else>
            <h1>Session Results</h1>
            <table class="results-table">
                <thead>
                    <tr>
                        <th>Rank</th>
                        <th>Description</th>
                        <th>Votes</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(option, index) in sessionData.options" :key="option.optionID"
                        :class="{ 'first-place': index === 0, 'second-place': index === 1, 'third-place': index === 2 }">
                        <td>{{ index + 1 }}</td>
                        <td>{{ option.description }}</td>
                        <td>{{ option.voteTally }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script lang="ts">
import { getAPIUrl } from '@/utils';
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'SessionResults',
    components: {

    },
    data() {
        return {
            error: '',
            isSessionOwner: false,
            sessionData: {
                "consensus": false,
                "options": [
                    {
                        "optionID": 0,
                        "description": "Description text # 1",
                        "voteTally": 7
                    },
                    {
                        "optionID": 1,
                        "description": "Description text # 2",
                        "voteTally": 3
                    },
                    {
                        "optionID": 2,
                        "description": "Description text # 3",
                        "voteTally": 5
                    },
                    {
                        "optionID": 3,
                        "description": "Description text # 4",
                        "voteTally": 0
                    },
                    {
                        "optionID": 4,
                        "description": "Description text # 5",
                        "voteTally": 1
                    }

                ]
            }
        };
    },

    methods: {
        async checkIfSessionExists() {

            if (!this.sessionID) {
                //push to home
                this.$router.push('/');
                return;
            }

            let url = getAPIUrl() + '/getSessionInfo?session=' + this.sessionID;

            let response = await fetch(url);

            let data = await response.json();

            if (data.status == 400) {
                this.error = 'Session not found';
            } else {

                if (data.is_active) {
                    this.$router.push('/join/' + this.sessionID);
                }

                this.sessionData = data;

            }

        },
    },
    mounted() {
        //this.checkIfSessionExists();
    },
    computed: {
        sessionID(): string | string[] | undefined {
            return this.$route.params.id;
        }
    },
});
</script>


<style lang="scss">
.home {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    padding: 2rem;
    min-height: 100vh;
}

.error {
    color: #dc3545;
    /* Error message color */
}

.results-table {
    border-collapse: separate;
    border-spacing: 0 15px;
    /* Adds spacing between rows */
    background-color: var(--background);
    box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
    border-radius: 8px;
    padding: 2rem;
    min-width: 400px;
}

.results-table th,
.results-table td {
    text-align: left;
    padding: 15px;
    background-color: var(--background);
    color: var(--text-color);
}

.results-table th {
    position: sticky;
    top: 0;
    background-color: inherit;
    font-size: 1rem;
}

.first-place td {
    background-color: var(--primary);
    color: #fff;
}

.second-place td {
    background-color: var(--secondary);
    color: #fff;
}


/* Removes border from the bottom row for rounded corners */
.results-table tr:last-child td {
    border-radius: 0 0 8px 8px;
}
</style>