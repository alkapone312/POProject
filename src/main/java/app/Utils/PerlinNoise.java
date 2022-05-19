package app.Utils;

import app.Reference;
import java.util.Random;

public class PerlinNoise {

    private float[] fSeed;

    public PerlinNoise()
    {
        fSeed = new float[Reference.WIDTH];
        fillSeed();
    }

    public PerlinNoise(int seedWidth)
    {
        fSeed = new float[seedWidth];
        fillSeed();
    }

    private void fillSeed()
    {
        Random r = new Random();
        for (int i = 0 ; i < fSeed.length; i++)
        {
            fSeed[i] = r.nextFloat();
        }
    }

    public float[] PerlinNoise1D(int nCount, int nOctaves, float fBias)
    {
        float[] fOutput = new float[nCount];
        // Used 1D Perlin Noise
        for (int x = 0; x < nCount; x++)
        {
            float fNoise = 0.0f;
            float fScaleAcc = 0.0f;
            float fScale = 1.0f;

            for (int o = 0; o < nOctaves; o++)
            {
                int nPitch = nCount >> o;
                int nSample1 = (x / nPitch) * nPitch;
                int nSample2 = (nSample1 + nPitch) % nCount;

                float fBlend = (float)(x - nSample1) / (float)nPitch;

                float fSample = (1.0f - fBlend) * this.fSeed[nSample1] + fBlend * this.fSeed[nSample2];

                fScaleAcc += fScale;
                fNoise += fSample * fScale;
                fScale = fScale / fBias;
            }

            // Scale to seed range
            fOutput[x] = fNoise / fScaleAcc;
        }
        return fOutput;
    }

    public float[][] PerlinNoise2D(int nWidth, int nHeight, int nOctaves, float fBias)
    {
        float[][] fOutput = new float[nWidth][nHeight];
        // Used 1D Perlin Noise
        for (int x = 0; x < nWidth; x++)
            for (int y = 0; y < nHeight; y++)
            {
                float fNoise = 0.0f;
                float fScaleAcc = 0.0f;
                float fScale = 1.0f;

                for (int o = 0; o < nOctaves; o++)
                {
                    int nPitch = nWidth >> o;
                    int nSampleX1 = (x / nPitch) * nPitch;
                    int nSampleY1 = (y / nPitch) * nPitch;

                    int nSampleX2 = (nSampleX1 + nPitch) % nWidth;
                    int nSampleY2 = (nSampleY1 + nPitch) % nHeight;

                    float fBlendX = (float)(x - nSampleX1) / (float)nPitch;
                    float fBlendY = (float)(y - nSampleY1) / (float)nPitch;

                    float fSampleT = (1.0f - fBlendX) * this.fSeed[nSampleY1 * nWidth + nSampleX1] + fBlendX * this.fSeed[nSampleY1 * nWidth + nSampleX2];
                    float fSampleB = (1.0f - fBlendX) * this.fSeed[nSampleY2 * nWidth + nSampleX1] + fBlendX * this.fSeed[nSampleY2 * nWidth + nSampleX2];

                    fScaleAcc += fScale;
                    fNoise += (fBlendY * (fSampleB - fSampleT) + fSampleT) * fScale;
                    fScale = fScale / fBias;
                }

                // Scale to seed range
                fOutput[x][y] = fNoise / fScaleAcc;
            }
        return fOutput;
    }
}