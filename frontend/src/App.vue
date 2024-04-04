<template>
  <router-view />
</template>


<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap");
:root {
  --primary: #ea4080;
  --primary-light: #ea4081a1;
  --secondary: #ee805f;
  --secondary-light: #ee805fa1;
  --gray: #828693;
  --primary-text: #373d4f;
}

#app {
  font-optical-sizing: auto;
  font-style: normal;
  font-variant: normal;
  font-family: Inter;

}

h1 {
  font-family: Inter;
  font-size: 56px;
  font-style: normal;
  font-weight: 900;
  line-height: normal;
  color: var(--primary-text);
  text-align: center;
}

h2 {
  color: var(--primary-text);
  font-family: Inter;
  font-size: 38.292px;
  font-style: normal;
  font-weight: 600;
  line-height: 140%;
  text-align: center;
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
  align-items: center;
  cursor: pointer;
  text-transform: uppercase;
  text-align: center;
  gap: 0.5rem;
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

  svg {
    margin-right: 0.5rem;
    width: 20px;
    height: 20px;
  }

  &.--small {
    padding: 10px 20px;
    font-size: 14.984px;
  }


  &.--secondary {
    //just gray color and border
    background-image: none;
    background-color: transparent;
    color: var(--gray);
    border-radius: 67.179px;
    border: 1px solid var(--gray);
    box-shadow: 0px 3.023px 5.038px 0px rgba(0, 0, 0, 0.15);
  }

  &.--disabled {
    background: #E5E5E5;
    color: #828693;
    cursor: not-allowed;
  }

  &.--danger {
    background: #ea4080;
    color: #FFF;
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



.option {
  width: 265px;
  height: 49px;
  flex-shrink: 0;
  cursor: pointer;

  border-radius: 16px;
  background: #FFF;
  border: 1px solid #E5E5E5;

  font-family: Inter;
  font-size: 19px;
  font-style: normal;
  font-weight: 500;
  line-height: 140%;
  /* 26.6px */

  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0.5rem;


  &.--selected {
    background: #EA5B6E;
    border: 1px solid #EA5B6E;

    color: #FFF;
  }

  &:hover {
    border: 1px solid #EA5B6E;
  }



}
</style>
