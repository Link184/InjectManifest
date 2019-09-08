package com.whyn.plugin;

import com.yn.compiler.ManifestCollectionProcessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;

@RunWith(JUnit4.class)
public class Debugger {
    @Test
    public void processorTest() {
        ManifestCollectionProcessor manifestCollectionProcessor = (new ManifestCollectionProcessor());
        RoundEnvironment environmentMock = Mockito.mock(RoundEnvironment.class);
        Mockito.when(environmentMock.processingOver()).thenReturn(false);

        manifestCollectionProcessor.process(Mockito.mock(Set.class), environmentMock);
    }
}
