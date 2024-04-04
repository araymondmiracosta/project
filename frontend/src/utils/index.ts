

 function getAPIUrl() {
  if (process.env.NODE_ENV === 'production') {

    throw new Error("Not implemented. Production environment not supported yet.");
    
  }
  return 'http://localhost:8080';
}


export { getAPIUrl };