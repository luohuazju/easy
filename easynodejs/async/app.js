function timeout(ms) {
    console.log(`timeout start`);    
    return new Promise(resolve => {
        try { 
        setTimeout(() => {
            console.log(`timeout cb fire after ${ms} ms`);
            resolve();
        }, ms);
        } catch(error){
            reject(error);
        }
    });
}

async function main() {
    console.log(`main start`); 
    await timeout(5e3); 
    console.log(`main end`);
}

main();
