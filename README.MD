<b>Daisy - A dice bot for telegram (and more).</b>

<b>Set up</b>
<ol>
	<li>Get your Telegram bot API key from <a href="https://web.telegram.org/#/im?p=@BotFather">BotFather</a></li>
	<li>Get your Cleverbot API key from <a href="https://www.cleverbot.com/api/">Cleverbot</a></li>
	<li>Configure DB parameters (just create an empty database, tables will be created on first run).</li>
</ol>


<b>Commands</b>
<ul>
	<li>/d[x]</li><p>Rolls a dice with XXX faces.</p>
	<li>/r[x]</li><p>Rolls a red dice. Red dices have 6 faces, THREE of them are zeroes, and the rest are 1, 2, and 3.</p>
	<li>/b[x]</li><p>Rolls a white dice. White dices have 6 faces, TWO of them are zeroes, and the rest are 1, and 2.</p>
</ul>
<b>Rolls can have a bonus (i.e +1, +2, or even a malus, -1, -2, ...) appended to the string.</b> 

<b>Javascript Engine</b>

<p>Daisy have a built in Javascript engine that you could access by preceding a message with double slash (//).</p> 
<p>You can use it to perform simple math using Javascript or even track character sheets in user defined objects!
There are wrappers for standard roll commands as //d(ammount), //w(ammount), //r(ammount).</p>

<p>Daisy have another functionalities not yet documented as they're still under develompent. I encourage you to check the code and discover them!.</p>

<p>You can find a instance of <a href="https://web.telegram.org/#/im?p=@diceroller_bot">Daisy on Telegram!</a></p> 

<b>Chat with Cleverbot!</b>
<p>Prefix a message with the command /dc [your message]. Cleverbot will answer you!</p>