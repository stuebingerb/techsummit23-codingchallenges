export{weightedSort};let weightedSort=(t)=>[...new Set(t.replace(/['-.,!?]/g,"").split(/ +/))].map(w=>[w,[...w].reduce((p,c)=>p+=c.charCodeAt(),0)]).sort().reverse().sort((a,b)=>b[1]-a[1]).map(e=>e[0]+"|"+e[1]).join`\n`;