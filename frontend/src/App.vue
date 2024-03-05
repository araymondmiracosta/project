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
  all: unset;
  font-family: "Inter", sans-serif;
  font-weight: 900;
  font-size: 56px;
  line-height: 3.5rem;
  letter-spacing: 0.1em;
  color: var(--primary-text);
}

p {
  font-family: "Inter", sans-serif;
  font-weight: 300;
  font-size: 19px;
  text-align: center;
  line-height: 2rem;
  letter-spacing: 0.1em;
  color: var(--primary-text);
}

.button {
  font-family: "Inter", sans-serif;
  font-weight: 500;
  font-size: 18px;
  line-height: 2rem;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  color: #fff;
  //linear gradient from EA4080 to EE805F
  background-image: linear-gradient(45deg, var(--primary), var(--secondary));
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 99px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;

  &:hover {
  }

  &.--secondary {
    //just gray color and border
    background-image: none;
    background-color: transparent;
    color: var(--gray);
    border: 2px solid var(--gray);
  }
}
</style>
