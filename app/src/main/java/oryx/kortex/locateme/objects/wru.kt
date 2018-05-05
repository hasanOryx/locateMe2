package oryx.kortex.locateme.objects

val wru = """
    (وينك)|(وينكي)|(وينكم)|(فينك)|(فينكي)|(فينكم)|(أين أنت)|(اين انت)|(أين أنتم)|(اين انتم)
    |(?i)
    (where|were|\bw)\s*(are|r)\s*(you|u)|(Dónde estás)|(Donde estas)|(Ku jeni ju)
    |(Non zaude)|(Дзе вы знаходзіцеся)|(Gdje si ti)|(Къде си)|(On ets)|(Gdje si)|(Kde jsi)
    |(Hvor er du)|(Waar ben je)|(Kus sa oled)|(Missä sinä olet)|(Où es tu)|(Onde estás)
    |(Wo bist du)|(Που είσαι)|(Merre jársz)|(Hvar ertu)|(Hvor er du)|(Gdzie jesteś)
    |(Onde está você)|(Unde esti)|(Где ты)|(Где си)|(Kde si)|(Kje si)|(Var är du)
    |(Де ти)|(Ble ydych chi)|(ווו זענען איר)|(Որտեղ եք դուք)|(Haradasan)|(你在哪里)|(你在哪裡)
    |(სად ხარ)|(Ubi es)|(Qhov twg yog koj)|(どこにいますか)|(Сен қайдасың)|(Та хаана байна вэ)
    |(Ту дар куҷо)|(คุณอยู่ที่ไหน)|(اپ کہاں ہیں)|(Qayerdasiz)|(Bạn đang ở đâu)|(איפה אתה)|(کجایی)
    |(Neredesin)|(Waar is jy)|(Muli kuti)|(Ina ku ke)|(Ebee ka ị nọ)|(U hokae)
    |(Xaggee baad joogtaa)|(Uko wapi)|(Ibo lo wa)|(Ukuphi)|(Hain ka)|(Nasaan ka)|(Kamu di mana)
    |(Aiza ianao)|(Di manakah anda)|(Kei hea koe)|(Kie vi estas)|(Ki kote w ye)
    (?-i)""".trimIndent().replace("\n","").toRegex(RegexOption.IGNORE_CASE)