package com.ahmed.android.beatbox

import org.hamcrest.core.Is.`is`
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {
    private lateinit var sound: Sound
    //Subject object is under test
    private lateinit var subject: SoundViewModel
    private lateinit var beatBox:BeatBox

    @Before
    fun setUp() {
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetpath")
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNAmeAsTitle(){
        assertThat(subject.title, `is`(sound.name))
    }

    @Test
    fun callsBeatboxOnButtonClicked(){
        subject.onButtonClicked()
        verify(beatBox)
        beatBox.play(sound)
    }
}