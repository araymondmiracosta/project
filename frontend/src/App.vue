<template>
  <router-view />
</template>

<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap");

:root {
  --primary: #ea4080;
  --secondary: #ee805f;
  --gray: #828693;
  --primary-text: #373d4f;
}

#app {
  font-optical-sizing: auto;
  font-style: normal;
  font-variant: normal;
}

h1 {
  font-family: Inter;
  font-size: 56px;
  font-style: normal;
  font-weight: 900;
  line-height: normal;
  color: var(--primary-text);
}

h2 {
  color: #010001;
  font-family: Inter;
  font-size: 38.292px;
  font-style: normal;
  font-weight: 600;
  line-height: 140%;
  /* 53.609px */
}

p {
  text-align: center;
  font-family: Inter;
  font-size: 19.482px;
  font-style: normal;
  font-weight: 500;
  line-height: 25.192px;
  /* 129.31% */
  color: var(--primary-text);
}

.button {
  all: unset;
  display: flex;
  justify-content: center;

  padding: 14.108px 43.885px;
  align-items: center;
  border-radius: 67.179px;
  background: linear-gradient(90deg, #EA4080 0%, #EE805F 100%);
  box-shadow: 0px 3.023px 5.038px 0px rgba(0, 0, 0, 0.15);
  border: none;
  color: #FFF;
  font-family: Inter;
  font-size: 18.138px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;


  &.--secondary {
    //just gray color and border
    background-image: none;
    background-color: transparent;
    color: var(--gray);
    border-radius: 67.179px;
    border: 1px solid var(--gray);
    box-shadow: 0px 3.023px 5.038px 0px rgba(0, 0, 0, 0.15);
  }
}



input {
  padding: 14.108px 43.885px;
  border-radius: 67.179px;
  border: 1px solid #E5E5E5;
  font-family: Inter;
  font-size: 18.138px;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
  color: var(--primary-text);
  display: flex;
  margin: 1rem;
}
</style>
